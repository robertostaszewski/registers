package com.assignment.registers.exceptions;

/**
 * Thrown to indicate that transfer is not allowed for some reason.
 */
public class TransferNotAllowedException extends RuntimeException {
    public TransferNotAllowedException(String message) {
        super(message);
    }
}
