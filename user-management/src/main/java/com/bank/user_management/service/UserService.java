package com.bank.user_management.service;

import com.bank.card_generation.entities.Card;
import com.bank.user_management.CardValidationClient;
import com.bank.user_management.entities.*;
import com.bank.user_management.exception.UserNotFoundException;
import com.bank.user_management.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    public UserResponseDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userConverter::convertToDTO).orElse(null);
    }

    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        user = userProcessor.process(user);
        User savedUser = userRepository.save(user);
        return userConverter.convertToDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();


            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            } else {
                userToUpdate.setPassword(userToUpdate.getPassword());
            }


            userToUpdate = userConverter.updateEntityFromDTO(userToUpdate, userDTO);

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

