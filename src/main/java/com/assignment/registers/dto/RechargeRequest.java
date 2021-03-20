package com.assignment.registers.dto;

/**
 * Class representing request required from user to recharge register.
 */
public class RechargeRequest {
    private long registerId;
    private double amount;

    /**
     * No args constructor needed for mapping purpose.
     */
    public RechargeRequest() {
    }

    public RechargeRequest(long registerId, double amount) {
        this.registerId = registerId;
        this.amount = amount;
    }

    public long getRegisterId() {
        return registerId;
    }

    public double getAmount() {
        return amount;
    }
}
