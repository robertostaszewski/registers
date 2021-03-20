package com.assignment.registers.controllers;

import com.assignment.registers.entities.Register;
import com.assignment.registers.exceptions.BalanceNotValidException;
import com.assignment.registers.exceptions.RegisterNotFoundException;
import com.assignment.registers.exceptions.TransferNotAllowedException;
import com.assignment.registers.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegistersControllerTest {
    private static final String RECHARGE_REQUEST = "{ \"registerId\": 1, \"amount\": 100.0 }";
    private static final String TRANSFER_REQUEST = "{ \"sourceId\": 1, \"destinationId\": 2, \"amount\": 100.0 }";
    private static final String DUPLICATE_IDS_TRANSFER_REQUEST = "{ \"sourceId\": 1, \"destinationId\": 1, \"amount\": 100.0 }";

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegistersController registersController;

    private MockMvc mockMvc;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(registersController).build();
    }

    @Test
    @DisplayName("Given recharge request. Post om recharge endpoint should call recharge on service")
    public void givenRechargeRequest_recharge_shouldCallRechargeOnService() throws Exception {
        mockMvc.perform(post("/rest/registers/operation/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RECHARGE_REQUEST))
                .andExpect(status().isOk());

        verify(registerService).recharge(1L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Registers service throws balance exception. Post on recharge endpoint should handle exception")
    public void serviceThrowsBalanceException_recharge_shouldHandleException() throws Exception {
        String balanceErrorMessage = "balance error message";
        doThrow(new BalanceNotValidException(balanceErrorMessage))
                .when(registerService).recharge(anyLong(), anyDouble());

        mockMvc.perform(post("/rest/registers/operation/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RECHARGE_REQUEST))
                .andExpect(status().isConflict())
                .andExpect(content().string(balanceErrorMessage));

        verify(registerService).recharge(1L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Registers service throws RegisterNotFoundException. Post on recharge endpoint should handle exception")
    public void serviceThrowsRegisterNotFoundException_recharge_shouldHandleException() throws Exception {
        String registerErrorMessage = "register error message";
        doThrow(new RegisterNotFoundException(registerErrorMessage))
                .when(registerService).recharge(anyLong(), anyDouble());

        mockMvc.perform(post("/rest/registers/operation/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RECHARGE_REQUEST))
                .andExpect(status().isNotFound())
                .andExpect(content().string(registerErrorMessage));

        verify(registerService).recharge(1L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Given transfer request. Post on transfer endpoint should call transfer on service")
    public void givenTransferRequest_transfer_shouldCallTransferOnService() throws Exception {
        mockMvc.perform(post("/rest/registers/operation/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TRANSFER_REQUEST))
                .andExpect(status().isOk());

        verify(registerService).transfer(1L, 2L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Registers service throws balance exception. Post on transfer endpoint should handle exception")
    public void serviceThrowsBalanceException_transfer_shouldHandleException() throws Exception {
        String balanceErrorMessage = "balance error message";
        doThrow(new BalanceNotValidException(balanceErrorMessage))
                .when(registerService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/rest/registers/operation/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TRANSFER_REQUEST))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(balanceErrorMessage));

        verify(registerService).transfer(1L, 2L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Registers service throws RegisterNotFoundException. Post on transfer endpoint should handle exception")
    public void serviceThrowsRegisterNotFoundException_transfer_shouldHandleException() throws Exception {
        String registerErrorMessage = "register error message";
        doThrow(new RegisterNotFoundException(registerErrorMessage))
                .when(registerService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/rest/registers/operation/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TRANSFER_REQUEST))
                .andExpect(status().isNotFound())
                .andExpect(content().string(registerErrorMessage));

        verify(registerService).transfer(1L, 2L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Registers service throws TransferException. Post on transfer endpoint should handle exception")
    public void serviceThrowsTransferException_transfer_shouldHandleException() throws Exception {
        String registerErrorMessage = "transfer error message";
        doThrow(new TransferNotAllowedException(registerErrorMessage))
                .when(registerService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/rest/registers/operation/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(DUPLICATE_IDS_TRANSFER_REQUEST))
                .andExpect(status().isForbidden())
                .andExpect(content().string(registerErrorMessage));

        verify(registerService).transfer(1L, 1L, 100.0);
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Given ask for balance. Calling get on get balance endpoint should return registers summary")
    public void givenAskForBalance_getBalance_shouldReturnRegistersSummary() throws Exception {
        when(registerService.getBalanceForAll())
                .thenReturn(Arrays.asList(
                        new Register(1L, "food", 300.0),
                        new Register(2L, "investments", 900.0)));

        mockMvc.perform(get("/rest/registers/balance"))
                .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("{" +
                "    \"registers\": [" +
                "        {" +
                "            \"id\": 1," +
                "            \"name\": \"food\"," +
                "            \"balance\": 300.0" +
                "        }," +
                "        {" +
                "            \"id\": 2," +
                "            \"name\": \"investments\"," +
                "            \"balance\": 900.0" +
                "        }" +
                "    ]" +
                "}"));

        verify(registerService).getBalanceForAll();
        verifyNoMoreInteractions(registerService);
    }

    @Test
    @DisplayName("Given ask for balance for no registers. Calling get on get balance endpoint should return empty registers summary")
    public void givenAskForBalanceForNoRegisters_getBalance_shouldReturnEmptyRegistersSummary() throws Exception {
        when(registerService.getBalanceForAll())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rest/registers/balance"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{ }"));

        verify(registerService).getBalanceForAll();
        verifyNoMoreInteractions(registerService);
    }
}