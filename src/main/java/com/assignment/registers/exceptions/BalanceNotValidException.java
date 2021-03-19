package com.assignment.registers.exceptions;

public class BalanceNotValidException extends RuntimeException {
    public BalanceNotValidException(String message) {
        super(message);
    }
}
