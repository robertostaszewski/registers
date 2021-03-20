package com.assignment.registers.exceptions;

/**
 * Thrown to indicate that balance of a register might became to invalid state e.g. less than 0.
 */
public class BalanceNotValidException extends RuntimeException {
    public BalanceNotValidException(String message) {
        super(message);
    }
}
