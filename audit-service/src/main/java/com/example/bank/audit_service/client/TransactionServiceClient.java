package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "card-transaction", url = "http://localhost:8009", path = "/api/transactions")
public interface TransactionServiceClient {

    @GetMapping("/{transactionId}")
    TransactionDTO getTransactionDetails(@PathVariable("transactionId") String transactionId);
}