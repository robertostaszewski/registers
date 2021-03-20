package com.assignment.registers.dto;

public class RechargeRequest {
    private long destinationId;
    private double amount;

    public RechargeRequest() {
    }

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
