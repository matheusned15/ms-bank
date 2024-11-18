package com.bank.card_transaction.service;


import com.bank.card_transaction.client.CardGenerationClient;
import com.bank.card_transaction.entity.Card;
import com.bank.card_transaction.entity.dto.CardDTO;
import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.dto.TransactionRequestDTO;
import com.bank.card_transaction.entity.dto.TransactionResponseDTO;
import com.bank.card_transaction.repository.CardRepository;
import com.bank.card_transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalArgumentException("Payer card not found with provided ID.");
        }
        if (recipientCardDTO == null) {
            throw new IllegalArgumentException("Recipient card not found with provided ID.");
        }


        if (payerCardDTO.getBalance() < request.getAmount()) {
            return createFailedTransactionResponse("Insufficient balance on the payer's card.");
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
        transaction.setTransactionAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setPayerCardId(payerCard);
        transaction.setUserId(payerCard.getUser().getId());
        transaction.setRecipientId(recipientCard.getUser().getId());
        transaction.setRecipientCardId(recipientCard);
        transaction.setDescription(request.getDescription());
        transaction.setStatus("SUCESS");

        transactionRepository.save(transaction);

        return createSuccessTransactionResponse(transaction, payerNewBalance, recipientNewBalance);
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

    private TransactionResponseDTO createSuccessTransactionResponse(Transaction transaction, double payerNewBalance, double recipientNewBalance) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactionId(transaction.getId());
        response.setStatus("SUCCESS");
        response.setMessage("Transaction completed successfully");
        response.setPayerNewBalance(payerNewBalance);
        response.setRecipientNewBalance(recipientNewBalance);
        response.setPayerId(transaction.getUserId());
        response.setRecipientId(transaction.getRecipientId());
        response.setAmount(transaction.getTransactionAmount());

        return response;
    }

    private TransactionResponseDTO createFailedTransactionResponse(String message) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setStatus("FAILED");
        response.setMessage(message);
        return response;
    }

    private TransactionResponseDTO convertToResponseDTO(Transaction transaction) {
        CardDTO payerId = cardGenerationClient.getCardById(transaction.getPayerCardId().getId());
        CardDTO recipientId = cardGenerationClient.getCardById(transaction.getRecipientCardId().getId());

        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getId());
        dto.setStatus(transaction.getStatus());
        dto.setMessage(transaction.getDescription());
        dto.setPayerNewBalance(payerId.getBalance());
        dto.setRecipientNewBalance(recipientId.getBalance());

        return dto;
    }

    public List<TransactionResponseDTO> getAllTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.getAllTransactionsByUserId(userId);

        return transactions.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getTransactionById(Long id){
        Transaction transaction = transactionRepository.getByTransaction(id);

        CardDTO payerId = cardGenerationClient.getCardById(transaction.getPayerCardId().getId());
        CardDTO recipientId = cardGenerationClient.getCardById(transaction.getRecipientCardId().getId());

        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setPayerId(transaction.getUserId());
        dto.setAmount(transaction.getTransactionAmount());
        dto.setRecipientId(transaction.getRecipientId());
        dto.setTransactionId(transaction.getId());
        dto.setMessage(transaction.getDescription());
        dto.setStatus(transaction.getStatus());
        dto.setPayerNewBalance(payerId.getBalance());
        dto.setRecipientNewBalance(recipientId.getBalance());

        return dto;
    }

}

