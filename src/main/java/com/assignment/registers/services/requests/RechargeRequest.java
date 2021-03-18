package com.assignment.registers.services.requests;

public class RechargeRequest {
    private final Long destinationRegisterId;
    private final double amount;

    public RechargeRequest(Long destinationRegisterId, double amount) {
        this.destinationRegisterId = destinationRegisterId;
        this.amount = amount;
    }

    public Long getDestinationRegisterId() {
        return destinationRegisterId;
    }

    public double getAmount() {
        return amount;
    }
}
