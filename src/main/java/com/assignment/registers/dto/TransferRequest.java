package com.assignment.registers.dto;

public class TransferRequest {
    private final long sourceId;
    private final long destinationId;
    private final double amount;

    public TransferRequest(long sourceId, long destinationId, double amount) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.amount = amount;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public double getAmount() {
        return amount;
    }
}
