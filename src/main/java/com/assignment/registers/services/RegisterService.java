package com.assignment.registers.services;

import com.assignment.registers.entities.Register;
import com.assignment.registers.dto.RechargeRequest;
import com.assignment.registers.dto.TransferRequest;
import com.assignment.registers.exceptions.BalanceNotValidException;
import com.assignment.registers.exceptions.RegisterNotFoundException;
import com.assignment.registers.exceptions.TransferNotAllowedException;

import java.util.List;

/**
 * Interface exposing basic registers operations.
 */
public interface RegisterService {

    /**
     * Recharge an existing register with given amount.
     *
     * @param registerId id of an existing register.
     * @param amount the difference about which requested register will be changed.
     * @throws BalanceNotValidException if the requested amount makes balance negative.
     * @throws RegisterNotFoundException if register of given id does not exist.
     */
    void recharge(long registerId, double amount);

    /**
     * Transfer given amount between two existing registers.
     *
     * @param sourceId id of an existing source register.
     * @param destinationId id of an existing destination register.
     * @param amount the difference about which source register will be charged and destination increased.
     * @throws BalanceNotValidException if the requested amount is greater than balance of source register or
     *         makes destination balance negative.
     * @throws RegisterNotFoundException if any register from given ids does not exist.
     * @throws TransferNotAllowedException if given ids are equal.
     */
    void transfer(long sourceId, long destinationId, double amount);

    /**
     * Get current balance of all registers.
     *
     * @return list of all {@link Register registers} with current balance.
     */
    List<Register> getBalanceForAll();
}
