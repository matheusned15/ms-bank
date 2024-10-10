package com.bank.card_transaction.service;




import com.bank.card_transaction.client.CardGenerationClient;
import com.bank.card_transaction.client.CardValidationClient;
import com.bank.card_transaction.entity.Card;
import com.bank.card_transaction.entity.TransactionType;
import com.bank.card_transaction.entity.dto.CardDTO;
import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.dto.TransactionRequestDTO;
import com.bank.card_transaction.entity.dto.TransactionResponseDTO;
import com.bank.card_transaction.repository.CardRepository;
import com.bank.card_transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardTransactionService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardGenerationClient cardGenerationClient;

    public CardTransactionService(CardRepository cardRepository, TransactionRepository transactionRepository, CardGenerationClient cardGenerationClient) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.cardGenerationClient = cardGenerationClient;
    }

    @Transactional
    public TransactionResponseDTO processTransaction(TransactionRequestDTO request) {
        CardDTO payerCardDTO = cardGenerationClient.getCardById(request.getPayerCardId());
        CardDTO recipientCardDTO = cardGenerationClient.getCardById(request.getRecipientCardId());


        if (payerCardDTO == null) {
            throw new IllegalArgumentException("Cartão do pagador não encontrado com o ID fornecido.");
        }
        if (recipientCardDTO == null) {
            throw new IllegalArgumentException("Cartão do recebedor não encontrado com o ID fornecido.");
        }


        if (payerCardDTO.getBalance() < request.getAmount()) {
            return createFailedTransactionResponse("Saldo insuficiente no cartão do pagador.");
        }


        double payerNewBalance = payerCardDTO.getBalance() - request.getAmount();
        double recipientNewBalance = recipientCardDTO.getBalance() + request.getAmount();


        cardGenerationClient.updateCardBalance(payerCardDTO.getId(), payerNewBalance);
        cardGenerationClient.updateCardBalance(recipientCardDTO.getId(), recipientNewBalance);


        Card payerCard = saveOrUpdateCard(payerCardDTO);
        Card recipientCard = saveOrUpdateCard(recipientCardDTO);


        return registerTransaction(payerCard, recipientCard, request, payerNewBalance, recipientNewBalance);
    }

    private Card saveOrUpdateCard(CardDTO cardDTO) {
        Optional<Card> existingCard = cardRepository.findById(cardDTO.getId());
        if (existingCard.isPresent()) {
            return existingCard.get();
        }


        Card newCard = convertDtoToEntity(cardDTO);
        return cardRepository.save(newCard);
    }

    private TransactionResponseDTO registerTransaction(Card payerCard, Card recipientCard, TransactionRequestDTO request, double payerNewBalance, double recipientNewBalance) {

        Transaction transaction = new Transaction();
        transaction.setPayerCard(payerCard);
        transaction.setRecipientCard(recipientCard);
        transaction.setTransactionAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setMerchantName(request.getDescription());
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setUserId(payerCard.getId());


        transaction.setPayerNewBalance(BigDecimal.valueOf(payerNewBalance));
        transaction.setRecipientNewBalance(BigDecimal.valueOf(recipientNewBalance));
        transaction.setStatus("SUCCESS");


        transactionRepository.save(transaction);


        return createSuccessTransactionResponse(payerNewBalance, recipientNewBalance);
    }


    private Card convertDtoToEntity(CardDTO cardDTO) {
        Card card = new Card();
        card.setId(cardDTO.getId());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardHolderName(cardDTO.getCardHolderName());
        card.setExpirationDate(cardDTO.getExpirationDate());
        card.setCvv(cardDTO.getCvv());
        card.setBalance(cardDTO.getBalance());
        return card;
    }


    private TransactionResponseDTO createSuccessTransactionResponse(double payerNewBalance, double recipientNewBalance) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setStatus("SUCCESS");
        response.setMessage("Transação realizada com sucesso");
        response.setPayerNewBalance(payerNewBalance);
        response.setRecipientNewBalance(recipientNewBalance);
        return response;
    }


    private TransactionResponseDTO createFailedTransactionResponse(String message) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setStatus("FAILED");
        response.setMessage(message);
        return response;
    }

    public List<TransactionResponseDTO> getTransactionsByUserId(Long cardId) {
        List<Transaction> transactions = transactionRepository.findByPayerCard_IdOrRecipientCard_Id(cardId, cardId);


        return transactions.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private TransactionResponseDTO convertToResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setStatus(transaction.getStatus());
        dto.setMessage("Transação encontrada");
        dto.setPayerNewBalance(transaction.getPayerNewBalance().doubleValue());
        dto.setRecipientNewBalance(transaction.getRecipientNewBalance().doubleValue());
        return dto;
    }

    public List<TransactionResponseDTO> getAllTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByPayerCard_UserIdOrRecipientCard_UserId(userId, userId);


        return transactions.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
}

