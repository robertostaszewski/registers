package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.services.requests.RechargeRequest;
import com.assignment.registers.services.requests.TransferRequest;

import java.util.List;

public interface RegisterService {
    void recharge(RechargeRequest rechargeRequest);

    void transfer(TransferRequest transferRequest);

    List<Register> getBalanceForAllRegisters();
}
