package com.bank.card_generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardGenerationApplication.class, args);
	}

}
