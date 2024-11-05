package com.bank.card_validation.service;

import com.bank.card_validation.client.AuditClient;
import com.bank.card_validation.entity.dto.AuditDTO;
import com.bank.card_validation.entity.dto.CardValidationRequestDTO;
import com.bank.card_validation.entity.dto.CardValidationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.regex.Pattern;

@Service
public class CardValidationService {

    private final AuditClient auditClient;

    @Autowired
    public CardValidationService(AuditClient auditClient) {
        this.auditClient = auditClient;
    }

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

    public static boolean isValidCardNumberFormat(String cardNumber) {
        return Pattern.matches("^[456]\\d{15}$", cardNumber);
    }

    public static boolean isExpirationDateValid(String expirationDate) {
        YearMonth currentYearMonth = YearMonth.now();
        try {
            YearMonth cardYearMonth = YearMonth.parse(expirationDate, java.time.format.DateTimeFormatter.ofPattern("MM/yy"));
            return cardYearMonth.isAfter(currentYearMonth);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCvvValid(String cvv) {
        return Pattern.matches("^\\d{3,4}$", cvv);
    }

    public static boolean isCardHolderNameValid(String cardHolderName) {
        return cardHolderName != null && !cardHolderName.trim().isEmpty();
    }

    public boolean validateCard(CardValidationRequestDTO cardValidationRequest) {
        return isCardHolderNameValid(cardValidationRequest.getCardHolderName()) &&
                isLuhnValid(cardValidationRequest.getCardNumber()) &&
                isValidCardNumberFormat(cardValidationRequest.getCardNumber()) &&
                isExpirationDateValid(cardValidationRequest.getExpirationDate()) &&
                isCvvValid(cardValidationRequest.getCvv()) &&
                validateCardAndProcessTransaction(cardValidationRequest);
    }

    public boolean validateCardAndProcessTransaction(CardValidationRequestDTO requestDTO) {
        CardValidationResponseDTO cardResponse = new CardValidationResponseDTO();
        cardResponse.setCvv(requestDTO.getCvv());
        cardResponse.setExpirationDate(requestDTO.getExpirationDate());
        cardResponse.setAmount(requestDTO.getAmount());

        if (!validateCardDetails(requestDTO, cardResponse)) {
            return false;
        }

        if (!validateBalance(cardResponse.getAmount(), requestDTO.getAmount())) {
            return false;
        }

       // double novoSaldo = cardResponse.getAmount() - requestDTO.getAmount();


        AuditDTO auditDTO = new AuditDTO("CardValidation", "Card Validated: " + requestDTO.getCardNumber(), LocalDateTime.now());
        auditClient.sendAuditEvent(auditDTO);

        return true;
    }

    private boolean validateCardDetails(CardValidationRequestDTO requestDTO, CardValidationResponseDTO cardResponse) {
        return requestDTO.getCvv().equals(cardResponse.getCvv()) &&
                requestDTO.getExpirationDate().equals(cardResponse.getExpirationDate());
    }

    private boolean validateBalance(double currentBalance, double transactionAmount) {
        return currentBalance >= transactionAmount;
    }
}
