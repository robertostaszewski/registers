package com.assignment.registers.entities;

import com.assignment.registers.exceptions.BalanceNotValidException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double balance;

    public Register() {
    }

    public Register(Long id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = validateBalance(balance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Register recharge(double amount) {
        return new Register(id, name, balance + amount);
    }

    private double validateBalance(double balance) {
        if (balance < 0) {
            throw new BalanceNotValidException(
                    String.format("Register %s: Balance of a register could not be less than 0. " +
                            "Given invalid balance: %.2f", name, balance));
        }

        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Register register = (Register) o;
        return Double.compare(register.balance, balance) == 0 && Objects.equals(id, register.id) && Objects.equals(name, register.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}
