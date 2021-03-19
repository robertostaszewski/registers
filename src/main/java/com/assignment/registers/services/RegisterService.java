package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.dto.RechargeRequest;
import com.assignment.registers.dto.TransferRequest;

import java.util.List;

public interface RegisterService {
    void recharge(long sourceId, double amount);

    void transfer(long sourceId, long destinationId, double amount);

    List<Register> getBalanceForAll();
}
