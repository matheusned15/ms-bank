package com.bank.card_validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardValidationApplication.class, args);
	}

}
