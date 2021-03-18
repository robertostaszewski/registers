package com.assignment.registers.controllers;

import com.assignment.registers.controllers.dto.RegisterSummary;
import com.assignment.registers.services.RegisterService;
import com.assignment.registers.services.requests.RechargeRequest;
import com.assignment.registers.services.requests.TransferRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistersController {

    private final RegisterService registerService;

    public RegistersController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/rest/{registerId}/recharge")
    public void recharge(@PathVariable("registerId") long registerId, @RequestParam("amount") double amount) {
        registerService.recharge(new RechargeRequest(registerId, amount));
    }

    @PostMapping("/rest/{sourceRegisterId}/transfer")
    public void transfer(@PathVariable("sourceRegisterId") long sourceRegisterId,
                         @RequestParam("destinationRegisterId") long destinationRegisterId,
                         @RequestParam("amount") double amount) {
        registerService.transfer(new TransferRequest(sourceRegisterId, destinationRegisterId, amount));
    }

    @GetMapping("/rest/balance")
    public RegisterSummary getBalances() {
        return new RegisterSummary(registerService.getBalanceForAllRegisters());
    }
}
