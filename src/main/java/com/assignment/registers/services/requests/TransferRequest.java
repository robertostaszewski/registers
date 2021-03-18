package com.assignment.registers.services.requests;

public class TransferRequest {
    private final Long sourceRegisterId;
    private final Long destinationRegisterId;
    private final double amount;

    public TransferRequest(Long sourceRegisterId, Long destinationRegisterId, double amount) {
        this.sourceRegisterId = sourceRegisterId;
        this.destinationRegisterId = destinationRegisterId;
        this.amount = amount;
    }

    public Long getSourceRegisterId() {
        return sourceRegisterId;
    }

    public Long getDestinationRegisterId() {
        return destinationRegisterId;
    }

    public double getAmount() {
        return amount;
    }
}
