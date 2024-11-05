package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "transaction-service")
public interface TransactionServiceClient {

    @GetMapping("/transactions/{transactionId}")
    TransactionDTO getTransactionDetails(@PathVariable("transactionId") Long transactionId);
}
