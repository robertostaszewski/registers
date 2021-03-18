package com.assignment.registers.controllers.dto;

import com.assignment.registers.entities.Register;

import java.util.List;

public class RegisterSummary {
    private final List<Register> registers;

    public RegisterSummary(List<Register> registers) {
        this.registers = List.copyOf(registers);
    }

    public List<Register> getRegisters() {
        return registers;
    }
}
