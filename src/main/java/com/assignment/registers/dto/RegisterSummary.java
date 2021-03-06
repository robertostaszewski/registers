package com.assignment.registers.dto;

import com.assignment.registers.entities.Register;

import java.util.Collections;
import java.util.List;

/**
 * Class send back to user, contains details of registers.
 */
public class RegisterSummary {
    private final List<Register> registers;

    public RegisterSummary(List<Register> registers) {
        this.registers = Collections.unmodifiableList(registers);
    }

    public List<Register> getRegisters() {
        return registers;
    }

}
