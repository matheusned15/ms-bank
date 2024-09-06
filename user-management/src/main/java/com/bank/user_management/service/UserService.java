package com.bank.user_management.service;

import com.bank.user_management.CardValidationClient;
import com.bank.user_management.entities.User;
import com.bank.user_management.entities.UserDTO;
import com.bank.user_management.exception.UserNotFoundException;
import com.bank.user_management.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final UserProcessor userProcessor;
    private final UserFilters userFilters;
    private final UserConverter userConverter;

    @Autowired
    private final CardValidationClient cardValidationClient;

    @Autowired
    public UserService(UserRepository userRepository, CardValidationClient cardValidationClient,
                       UserProcessor userProcessor, UserFilters userFilters, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.cardValidationClient = cardValidationClient;
        this.userProcessor = userProcessor;
        this.userFilters = userFilters;
        this.userConverter = userConverter;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = userFilters.filterActiveUsers(users);
        return filteredUsers.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userConverter::convertToDTO).orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        user = userProcessor.process(user);
        User savedUser = userRepository.save(user);
        return userConverter.convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
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
}

