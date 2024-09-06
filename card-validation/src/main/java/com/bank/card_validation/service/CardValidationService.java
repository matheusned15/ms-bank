package com.bank.card_validation.service;


import com.bank.card_validation.client.CardServiceClient;
import com.bank.card_validation.entity.dto.CardValidationRequestDTO;
import com.bank.card_validation.entity.dto.CardValidationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.regex.Pattern;

@Service
public class CardValidationService {

    @Autowired
    private static CardServiceClient cardServiceClient;

    // Verifica se o número do cartão é válido usando o Algoritmo de Luhn
    public static boolean isLuhnValid(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    // Verifica se o número do cartão tem o formato esperado
    public static boolean isValidCardNumberFormat(String cardNumber) {
        return Pattern.matches("^[456]\\d{15}$", cardNumber);
    }

    // Verifica se a data de expiração é válida e não está no passado
    public static boolean isExpirationDateValid(String expirationDate) {
        YearMonth currentYearMonth = YearMonth.now();
        try {
            YearMonth cardYearMonth = YearMonth.parse(expirationDate, java.time.format.DateTimeFormatter.ofPattern("MM/yy"));
            return cardYearMonth.isAfter(currentYearMonth);
        } catch (Exception e) {
            return false; // Formato de data inválido
        }
    }

    // Verifica se o CVV é válido (3 ou 4 dígitos)
    public static boolean isCvvValid(String cvv) {
        return Pattern.matches("^\\d{3,4}$", cvv);
    }

    // Verifica se o nome do titular do cartão é válido (não nulo ou vazio)
    public static boolean isCardHolderNameValid(String cardHolderName) {
        return cardHolderName != null && !cardHolderName.trim().isEmpty();
    }

    // Valida todos os aspectos do cartão, incluindo o nome do titular
    public static boolean validateCard(CardValidationRequestDTO cardValidationRequest) {
        return isCardHolderNameValid(cardValidationRequest.getCardHolderName()) &&
                isLuhnValid(cardValidationRequest.getCardNumber()) &&
                isValidCardNumberFormat(cardValidationRequest.getCardNumber()) &&
                isExpirationDateValid(cardValidationRequest.getExpirationDate()) &&
                isCvvValid(cardValidationRequest.getCvv()) &&
                validateCardAndProcessTransaction(cardValidationRequest);
    }

    public static boolean validateCardAndProcessTransaction(CardValidationRequestDTO requestDTO) {
        // Obter os dados do cartão a partir do microserviço de geração de cartões
        CardValidationResponseDTO cardResponse = cardServiceClient.getCardDetails(requestDTO.getCardNumber());

        // Validar os detalhes do cartão (número, CVV, data de expiração)
        if (!validateCardDetails(requestDTO, cardResponse)) {
            return false; // Dados do cartão inválidos
        }

        // Validar saldo
        if (!validateBalance(cardResponse.getBalance(), requestDTO.getAmount())) {
            return false; // Saldo insuficiente
        }

        // Atualizar saldo após a transação ser validada
        cardServiceClient.updateBalance(requestDTO.getCardNumber(), cardResponse.getBalance() - requestDTO.getAmount());

        return true; // Transação válida
    }

    private static boolean validateCardDetails(CardValidationRequestDTO requestDTO, CardValidationResponseDTO cardResponse) {
        return requestDTO.getCvv().equals(cardResponse.getCvv()) &&
                requestDTO.getExpirationDate().equals(cardResponse.getExpirationDate());
    }

    private static boolean validateBalance(double currentBalance, double transactionAmount) {
        return currentBalance >= transactionAmount; // Saldo suficiente
    }
}
