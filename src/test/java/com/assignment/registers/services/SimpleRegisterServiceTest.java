package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.exceptions.BalanceNotValidException;
import com.assignment.registers.exceptions.RegisterNotFoundException;
import com.assignment.registers.exceptions.TransferNotAllowedException;
import com.assignment.registers.repositories.RegisterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleRegisterServiceTest {

    @Mock
    private RegisterRepository registerRepository;

    @InjectMocks
    private SimpleRegisterService registerService;

    @Test
    @DisplayName("Given existing id and specified amount. Calling recharge should call recharge on register and save result")
    public void givenExistingIdAndSpecifiedAmount_recharge_shouldCallRechargeOnRegisterAndSaveResult() {
        long registerId = 1L;
        double amount = 300.0;

        Register register = mock(Register.class);
        Register rechargedRegister = mock(Register.class);

        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(register));
        when(register.recharge(anyDouble()))
                .thenReturn(rechargedRegister);

        registerService.recharge(registerId, amount);

        InOrder inOrder = inOrder(registerRepository, register, rechargedRegister);
        inOrder.verify(registerRepository).findById(registerId);
        inOrder.verify(register).recharge(amount);
        inOrder.verify(registerRepository).save(rechargedRegister);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given non existing id. Calling recharge should throw exception")
    public void givenNonExistingId_recharge_shouldThrowException() {
        long registerId = 1L;
        double amount = 300.0;

        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> registerService.recharge(registerId, amount))
                .isInstanceOf(RegisterNotFoundException.class);

        verify(registerRepository).findById(registerId);
        verifyNoMoreInteractions(registerRepository);
    }

    @Test
    @DisplayName("Given existing id and register recharge method throws exception. Calling recharge should rethrow this exception")
    public void givenExistingIdAndRegisterRechargeMethodThrowsException_recharge_shouldRethrowThisException() {
        long registerId = 1L;
        double amount = 300.0;

        Register register = mock(Register.class);

        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(register));
        when(register.recharge(anyDouble()))
                .thenThrow(BalanceNotValidException.class);

        assertThatThrownBy(() -> registerService.recharge(registerId, amount))
                .isInstanceOf(BalanceNotValidException.class);

        InOrder inOrder = inOrder(registerRepository, register);
        inOrder.verify(registerRepository).findById(registerId);
        inOrder.verify(register).recharge(amount);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given existing ids and specified amount. Calling transfer should call recharge on registers and save results")
    public void givenExistingIdsAndSpecifiedAmount_transfer_shouldCallRechargeOnRegistersAndSaveResults() {
        long sourceId = 1L;
        long destinationId = 2L;
        double amount = 300.0;

        Register sourceRegister = mock(Register.class);
        Register destinationRegister = mock(Register.class);

        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(sourceRegister))
                .thenReturn(Optional.of(destinationRegister));

        Register updatedSourceRegister = mock(Register.class);
        Register updatedDestinationRegister = mock(Register.class);

        when(sourceRegister.recharge(anyDouble()))
                .thenReturn(updatedSourceRegister);
        when(destinationRegister.recharge(anyDouble()))
                .thenReturn(updatedDestinationRegister);

        registerService.transfer(sourceId, destinationId, amount);

        InOrder inOrder = inOrder(registerRepository, sourceRegister, destinationRegister,
                updatedSourceRegister, updatedDestinationRegister);
        inOrder.verify(registerRepository).findById(sourceId);
        inOrder.verify(registerRepository).findById(destinationId);
        inOrder.verify(sourceRegister).recharge(-amount);
        inOrder.verify(destinationRegister).recharge(amount);
        inOrder.verify(registerRepository).save(updatedSourceRegister);
        inOrder.verify(registerRepository).save(updatedDestinationRegister);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given non existing source id. Calling transfer should throw exception")
    public void givenNonExistingSourceId_recharge_shouldThrowException() {
        long sourceId = 1L;
        long destinationId = 2L;
        double amount = 300.0;

        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> registerService.transfer(sourceId, destinationId, amount))
                .isInstanceOf(RegisterNotFoundException.class);

        verify(registerRepository).findById(sourceId);
        verify(registerRepository, never()).findById(destinationId);
        verifyNoMoreInteractions(registerRepository);
    }

    @Test
    @DisplayName("Given non existing destination id. Calling recharge should throw exception")
    public void givenNonExistingDestinationId_recharge_shouldThrowException() {
        long sourceId = 1L;
        long destinationId = 2L;
        double amount = 300.0;

        Register sourceRegister = mock(Register.class);
        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(sourceRegister))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> registerService.transfer(sourceId, destinationId, amount))
                .isInstanceOf(RegisterNotFoundException.class);

        InOrder inOrder = inOrder(registerRepository, sourceRegister);
        inOrder.verify(registerRepository).findById(sourceId);
        inOrder.verify(registerRepository).findById(destinationId);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given existing ids and source register recharge method throws exception. Calling transfer should rethrow this exception")
    public void givenExistingIdsAndSourceRegisterRechargeMethodThrowsException_transfer_shouldRethrowThisException() {
        long sourceId = 1L;
        long destinationId = 2L;
        double amount = 300.0;

        Register sourceRegister = mock(Register.class);
        Register destinationRegister = mock(Register.class);
        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(sourceRegister))
                .thenReturn(Optional.of(destinationRegister));
        when(sourceRegister.recharge(anyDouble()))
                .thenThrow(BalanceNotValidException.class);

        assertThatThrownBy(() -> registerService.transfer(sourceId, destinationId, amount))
                .isInstanceOf(BalanceNotValidException.class);

        InOrder inOrder = inOrder(registerRepository, sourceRegister, destinationRegister);
        inOrder.verify(registerRepository).findById(sourceId);
        inOrder.verify(registerRepository).findById(destinationId);
        inOrder.verify(sourceRegister).recharge(-amount);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given existing ids and destination register recharge method throws exception. Calling transfer should rethrow this exception")
    public void givenExistingIdsAndDestinationRegisterRechargeMethodThrowsException_transfer_shouldRethrowThisException() {
        long sourceId = 1L;
        long destinationId = 2L;
        double amount = 300.0;

        Register sourceRegister = mock(Register.class);
        Register destinationRegister = mock(Register.class);
        when(registerRepository.findById(anyLong()))
                .thenReturn(Optional.of(sourceRegister))
                .thenReturn(Optional.of(destinationRegister));
        when(destinationRegister.recharge(anyDouble()))
                .thenThrow(BalanceNotValidException.class);

        assertThatThrownBy(() -> registerService.transfer(sourceId, destinationId, amount))
                .isInstanceOf(BalanceNotValidException.class);

        InOrder inOrder = inOrder(registerRepository, sourceRegister, destinationRegister);
        inOrder.verify(registerRepository).findById(sourceId);
        inOrder.verify(registerRepository).findById(destinationId);
        inOrder.verify(sourceRegister).recharge(-amount);
        inOrder.verify(destinationRegister).recharge(amount);
        inOrder.verify(registerRepository, never()).save(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Given the same ids. Calling transfer should throw exception")
    public void givenTheSameIds_transfer_shouldThrowException() {
        long sourceId = 1L;
        long destinationId = 1L;
        double amount = 300.0;

        assertThatThrownBy(() -> registerService.transfer(sourceId, destinationId, amount))
                .isInstanceOf(TransferNotAllowedException.class);

        verifyNoInteractions(registerRepository);
    }

    @Test
    @DisplayName("given non empty repository. Calling getBalanceForAll should return all registers in repository")
    public void givenNonEmptyRepository_getBalanceForAll_shouldReturnAllRegistersInRepository() {
        Register register1 = mock(Register.class);
        Register register2 = mock(Register.class);
        when(registerRepository.findAll())
                .thenReturn(Arrays.asList(register1, register2));

        List<Register> registers = registerService.getBalanceForAll();

        assertThat(registers).containsExactly(register1, register2);
    }

    @Test
    @DisplayName("given empty repository. Calling getBalanceForAll should return empty list")
    public void givenEmptyRepository_getBalanceForAll_shouldReturnEmptyList() {
        when(registerRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThat(registerService.getBalanceForAll()).isEmpty();
    }
}