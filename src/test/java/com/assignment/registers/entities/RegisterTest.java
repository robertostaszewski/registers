package com.assignment.registers.entities;

import com.assignment.registers.exceptions.BalanceNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RegisterTest {

    @Test
    @DisplayName("Given valid balance. Instantiate new register should create register")
    public void givenValidBalance_newRegister_shouldCreateRegister() {
        assertThatNoException().isThrownBy(() -> new Register(1L, "Investments", 300.0));
    }

    @Test
    @DisplayName("Given invalid balance. Instantiate new register should throw exception")
    public void givenInvalidBalance_newRegister_shouldThrowException() {
        assertThatThrownBy(() -> new Register(1L, "Investments", -300.0))
                .isInstanceOf(BalanceNotValidException.class);
    }

    @Test
    @DisplayName("Given register. Recharge should create new register with changed balance")
    public void givenRegister_recharge_shouldCreateRegisterWithChangedBalance() {
        Register register = new Register(1L, "Investments", 300.0);

        Register recharged = register.recharge(200.0);

        assertThat(recharged).isNotEqualTo(register);
        assertThat(recharged.getBalance()).isEqualTo(500.0);
    }

    @Test
    @DisplayName("given register with balance. Requested withdraw larger than balance. Recharge should throw exception")
    public void givenRegisterRequestedWithdrawLargerThanBalance_recharge_shouldThrowException() {
        Register register = new Register(1L, "Investments", 300.0);

        assertThatThrownBy(() -> register.recharge(-400.0)).isInstanceOf(BalanceNotValidException.class);
    }
}