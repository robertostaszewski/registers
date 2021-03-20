package com.assignment.registers.exceptions;

/**
 * Thrown to indicate that register does not exist.
 */
public class RegisterNotFoundException extends RuntimeException {
    public RegisterNotFoundException(String message) {
        super(message);
    }
}
