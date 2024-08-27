package com.bank.user_management.service;

import com.bank.user_management.OtherServiceClient;
import com.bank.user_management.entities.User;
import com.bank.user_management.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Adicionando Feign Client para comunicação com outro microserviço
    @Autowired
    private final OtherServiceClient otherServiceClient;

    public UserService(OtherServiceClient otherServiceClient) {
        this.otherServiceClient = otherServiceClient;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // Exemplo de comunicação com outro microserviço
        String validationResponse = otherServiceClient.validateUser(user);

        if (!"VALID".equals(validationResponse)) {
            throw new IllegalArgumentException("User validation failed");
        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        // Outros campos a serem atualizados

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}

