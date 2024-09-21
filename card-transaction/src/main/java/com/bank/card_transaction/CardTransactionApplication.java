package com.bank.card_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardTransactionApplication.class, args);
	}

}
