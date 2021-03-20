package com.assignment.registers.dto;

public class TransferRequest {
    private long sourceId;
    private long destinationId;
    private double amount;

    public TransferRequest() {
    }

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
