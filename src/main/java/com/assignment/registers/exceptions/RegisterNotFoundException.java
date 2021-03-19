package com.assignment.registers.exceptions;

public class RegisterNotFoundException extends RuntimeException {
    public RegisterNotFoundException(String message) {
        super(message);
    }
}
