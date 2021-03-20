package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.exceptions.RegisterNotFoundException;
import com.assignment.registers.exceptions.TransferNotAllowedException;
import com.assignment.registers.repositories.RegisterRepository;

import java.util.List;

/**
 * Implementation of {@link RegisterService}. Use {@link RegisterRepository} to get registers,
 * invoke operations on them and saves the results.
 */
public class SimpleRegisterService implements RegisterService {
    private final RegisterRepository registerRepository;

    public SimpleRegisterService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public void recharge(long registerId, double amount) {
        Register register = findRegisterById(registerId);
        Register updatedRegister = register.recharge(amount);
        registerRepository.save(updatedRegister);
    }

    @Override
    public void transfer(long sourceId, long destinationId, double amount) {
        if (sourceId == destinationId) {
            throw new TransferNotAllowedException(
                    String.format("Source id and destinations id are the same. Source id %o, destination id %o",
                            sourceId, destinationId));
        }

        Register source = findRegisterById(sourceId);
        Register destination = findRegisterById(destinationId);

        Register updatedSource = source.recharge(-amount);
        Register updatedDestination = destination.recharge(amount);

        registerRepository.save(updatedSource);
        registerRepository.save(updatedDestination);
    }

    @Override
    public List<Register> getBalanceForAll() {
        return registerRepository.findAll();
    }

    private Register findRegisterById(long id) {
        return registerRepository.findById(id).orElseThrow(() ->
                new RegisterNotFoundException(String.format("Register with given id %o does not exist", id)));
    }
}
