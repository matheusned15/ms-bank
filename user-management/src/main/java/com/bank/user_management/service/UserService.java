package com.bank.user_management.service;

import com.bank.card_generation.entities.Card;
import com.bank.user_management.CardValidationClient;
import com.bank.user_management.entities.*;
import com.bank.user_management.exception.UserAlreadyExistsException;
import com.bank.user_management.exception.UserNotFoundException;
import com.bank.user_management.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final UserProcessor userProcessor;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserFilters userFilters;
    @Autowired
    private final UserConverter userConverter;


    @Autowired
    private final CardValidationClient cardValidationClient;

    @Autowired
    public UserService(UserRepository userRepository, CardValidationClient cardValidationClient,
                       UserProcessor userProcessor, PasswordEncoder passwordEncoder, UserFilters userFilters, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.cardValidationClient = cardValidationClient;
        this.userProcessor = userProcessor;
        this.passwordEncoder = passwordEncoder;
        this.userFilters = userFilters;
        this.userConverter = userConverter;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = userFilters.filterActiveUsers(users);
        return filteredUsers.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public UserResponseDTO createUser(UserRequestDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }
        User user = userConverter.convertToEntity(userDTO);
        user = userProcessor.process(user);
        User savedUser = userRepository.save(user);
        return userConverter.convertToDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();

            if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
                userToUpdate.setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
                userToUpdate.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
                userToUpdate.setPassword(hashedPassword);
            }
            userToUpdate.setUpdated_at(LocalDateTime.now());

            userToUpdate = userProcessor.process(userToUpdate);

            User updatedUser = userRepository.save(userToUpdate);
            return userConverter.convertToDTO(updatedUser);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    public Card convertToEntity(CardRequestDTO dto) {
        return new Card(
                dto.getId(),
                dto.getCardNumber(),
                dto.getCardHolderName(),
                dto.getCvv(),
                dto.getExpirationDate(),
                dto.getAge(),
                dto.getLimit()
        );
    }
}

