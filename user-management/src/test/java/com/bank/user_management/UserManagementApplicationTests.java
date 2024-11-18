package com.bank.user_management;


import com.bank.user_management.entities.User;
import com.bank.user_management.entities.dto.CardDTO;
import com.bank.user_management.entities.dto.UserRequestDTO;
import com.bank.user_management.entities.dto.UserResponseDTO;
import com.bank.user_management.repository.UserRepository;
import com.bank.user_management.service.UserConverter;
import com.bank.user_management.service.UserFilters;
import com.bank.user_management.service.UserProcessor;
import com.bank.user_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserManagementApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserConverter userConverter;

	@Mock
	private UserProcessor userProcessor;

	@Mock
	private UserFilters userFilters;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		when(userConverter.convertToEntity(any(UserRequestDTO.class)))
				.thenReturn(new User("mockUser", "mockEmail", "mockPassword", true, LocalDateTime.now(), LocalDateTime.now()));

		when(userConverter.convertToDTO(any(User.class)))
				.thenReturn(new UserResponseDTO(1L, "mockUser", "mockEmail", true, LocalDateTime.now(), LocalDateTime.now()));

		when(userProcessor.process(any(User.class)))
				.thenAnswer(invocation -> {
					User user = invocation.getArgument(1);
					assertNotNull(user, "User cannot be null during process");
					return user;
				});

		when(passwordEncoder.encode(any(CharSequence.class)))
				.thenAnswer(invocation -> "hashed_" + invocation.getArgument(0));

		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

	}

	@Test
	void testUpdateUser() {
		User existingUser = new User(1L, "originalUser", "original@example.com", "originalPassword",
				LocalDateTime.now().minusDays(10), LocalDateTime.now(), true, null);

		UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "updatedUser", "updated@example.com", true,
				LocalDateTime.now().minusDays(10), LocalDateTime.now());

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

		CardDTO cardDTO = new CardDTO();
		cardDTO.setId(1L);

		UserRequestDTO userRequestDTO = UserRequestDTO.builder()
				.username("updatedUser")
				.email("updated@example.com")
				.password("newPassword")
				.card(cardDTO)
				.build();

		UserResponseDTO result = userService.updateUser(1L, userRequestDTO);

		User processedUser = userProcessor.process(existingUser);
		assertNotNull(processedUser, "Processed user should not be null");


		verify(userRepository, times(1)).findById(1L);
		verify(userRepository, times(1)).save(any(User.class));
		verify(passwordEncoder, times(1)).encode("newPassword");
		assertEquals(userResponseDTO.getUsername(), result.getUsername());
	}

}
