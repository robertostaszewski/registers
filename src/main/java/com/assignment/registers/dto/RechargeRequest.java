package com.assignment.registers.dto;

public class RechargeRequest {
    private final long destinationId;
    private final double amount;

    public RechargeRequest(long destinationId, double amount) {
        this.destinationId = destinationId;
        this.amount = amount;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public double getAmount() {
        return amount;
    }
}
