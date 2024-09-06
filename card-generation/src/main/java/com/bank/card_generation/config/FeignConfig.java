package com.bank.card_generation.config;

import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        // Define o nível de logging do Feign
        return Logger.Level.FULL;
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password"); // Exemplo de autenticação básica, caso necessário
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 10000); // Timeout de 5 segundos para conexão e 10 segundos para resposta
    }

}


