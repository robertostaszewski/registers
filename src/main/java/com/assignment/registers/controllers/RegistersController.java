package com.assignment.registers.controllers;

import com.assignment.registers.dto.RechargeRequest;
import com.assignment.registers.dto.RegisterSummary;
import com.assignment.registers.dto.TransferRequest;
import com.assignment.registers.exceptions.BalanceNotValidException;
import com.assignment.registers.exceptions.RegisterNotFoundException;
import com.assignment.registers.services.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/registers")
public class RegistersController {

    private final RegisterService registerService;

    public RegistersController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/payment/recharge")
    public void recharge(@RequestBody RechargeRequest rechargeRequest) {
        registerService.recharge(rechargeRequest.getDestinationId(), rechargeRequest.getAmount());
    }

    @PostMapping("/payment/transfer")
    public void transfer(@RequestBody TransferRequest transferRequest) {
        registerService.transfer(transferRequest.getSourceId(),
                transferRequest.getDestinationId(),
                transferRequest.getAmount());
    }

    @GetMapping("/balance")
    public RegisterSummary getRegistersBalance() {
        return new RegisterSummary(registerService.getBalanceForAll());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(BalanceNotValidException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(RegisterNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
