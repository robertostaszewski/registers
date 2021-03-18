package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.services.requests.RechargeRequest;
import com.assignment.registers.services.requests.TransferRequest;
import com.assignment.registers.repositories.RegisterRepository;

import java.util.List;

public class SimpleRegisterService implements RegisterService {
    private final RegisterRepository registerRepository;

    public SimpleRegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public void recharge(RechargeRequest rechargeRequest) {
        Register register = registerRepository.findById(rechargeRequest.getDestinationRegisterId()).orElseThrow();
        Register updatedRegister = register.recharge(rechargeRequest.getAmount());
        registerRepository.save(updatedRegister);
    }

    @Override
    public void transfer(TransferRequest transferRequest) {
        Register source = registerRepository.findById(transferRequest.getSourceRegisterId()).orElseThrow();
        Register destination = registerRepository.findById(transferRequest.getDestinationRegisterId()).orElseThrow();

        Register updatedSource = source.recharge(-transferRequest.getAmount());
        Register updatedDestination = destination.recharge(transferRequest.getAmount());

        registerRepository.save(updatedSource);
        registerRepository.save(updatedDestination);
    }

    @Override
    public List<Register> getBalanceForAllRegisters() {
        return registerRepository.findAll();
    }
}
