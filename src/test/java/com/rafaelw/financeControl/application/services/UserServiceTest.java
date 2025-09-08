package com.rafaelw.financeControl.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.application.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.domain.factories.UserFactory;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import com.rafaelw.financeControl.domain.service.exceptions.EmailAlreadyExistsException;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

  @Mock
  private VerifyUserByEmail verifyUserByEmail;

  @Mock
  private JpaUserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private UserFactory userFactory;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService service;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should be able create a user")
  void createUser() {
    UserRequestDTO newUserRequest = new UserRequestDTO("Joel", "joel@gmail.com", "12345678");
    User domainUser = new User("Joel", "joel@gmail.com", "hashedPassword");
    UserPersist userPersist = new UserPersist(1L, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);
    UserResponseDTO expectedResponse = new UserResponseDTO(1L, "Joel", "joel@gmail.com",
        Role.COMMON, true);

    when(userFactory.create(newUserRequest.name(), newUserRequest.email(),
        "hashedPassword")).thenReturn(domainUser);
    when(passwordEncoder.encode(newUserRequest.password())).thenReturn("hashedPassword");
    when(userMapper.toPersist(domainUser)).thenReturn(userPersist);
    when(userRepository.save(userPersist)).thenReturn(userPersist);
    when(userMapper.toResponseDTO(userPersist)).thenReturn(expectedResponse);

    UserResponseDTO createdUser = service.insert(newUserRequest);

    assertNotNull(createdUser);
    assertEquals(expectedResponse.id(), createdUser.id());
    assertEquals(expectedResponse.name(), createdUser.name());
    assertEquals(expectedResponse.email(), createdUser.email());

    verify(passwordEncoder, times(1)).encode("12345678");
    verify(userRepository, times(1)).save(any(UserPersist.class));
    verify(userFactory, times(1)).create("Joel", "joel@gmail.com", "hashedPassword");
    verify(userMapper, times(1)).toPersist(domainUser);
    verify(userMapper, times(1)).toResponseDTO(userPersist);
  }

  @Test
  @DisplayName("Should not be able create a user with same email")
  void createUserWithSameEmail() {
    UserRequestDTO newUserRequest = new UserRequestDTO("Joel", "joel@gmail.com", "12345678");
    doThrow(new EmailAlreadyExistsException(newUserRequest.email()))
        .when(verifyUserByEmail).execute(newUserRequest.email());

    assertThatThrownBy(() -> service.insert(newUserRequest))
        .isInstanceOf(EmailAlreadyExistsException.class)
        .hasMessage("User with e-mail joel@gmail.com already exist");

    verify(verifyUserByEmail, times(1)).execute(any());
    verify(passwordEncoder, times(0)).encode(any());
    verify(userFactory, times(0)).create(any(), any(), any());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should be able to find all users")
  void findAllUsers() {
    UserPersist userPersist1 = new UserPersist(1L, "Joel", "joel@gmail.com", "12345678", true,
        Role.COMMON, null, null);
    UserPersist userPersist2 = new UserPersist(2L, "Maria", "maria@gmail.com", "12345678", true,
        Role.COMMON, null, null);
    List<UserPersist> usersPersist = List.of(userPersist1, userPersist2);

    UserResponseDTO responseDTO1 = new UserResponseDTO(1L, "Joel", "joel@gmail.com", Role.COMMON,
        true);
    UserResponseDTO responseDTO2 = new UserResponseDTO(2L, "Maria", "maria@gmail.com", Role.COMMON,
        true);

    when(userRepository.findAll()).thenReturn(usersPersist);
    when(userMapper.toResponseDTO(userPersist1)).thenReturn(responseDTO1);
    when(userMapper.toResponseDTO(userPersist2)).thenReturn(responseDTO2);

    List<UserResponseDTO> response = service.findAll();

    assertThat(response).isNotNull();
    assertThat(response).hasSize(2);
    assertThat(response.get(0).name()).isEqualTo("Joel");
    assertThat(response.get(1).name()).isEqualTo("Maria");

    verify(userRepository, times(1)).findAll();
    verify(userMapper, times(2)).toResponseDTO(any(UserPersist.class));
  }

  @Test
  @DisplayName("Should be able to find a user by id")
  void findUserById() {
    Long userId = 1L;
    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "12345678", true,
        Role.COMMON, null, null);
    UserResponseDTO userResponseDTO = new UserResponseDTO(userId, "Joel", "joel@gmail.com",
        Role.COMMON, true);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(userMapper.toResponseDTO(userPersist)).thenReturn(userResponseDTO);

    UserResponseDTO response = service.findById(userId);

    assertThat(response.id()).isEqualTo(userId);

    verify(userRepository, times(1)).findById(userId);
    verify(userMapper, times(1)).toResponseDTO(any(UserPersist.class));
  }

  @Test
  @DisplayName("Should not be able to find user with unexist id")
  void findUnexistUserId() {
    Long userId = 1L;

    when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));

    assertThatThrownBy(() -> {
      service.findById(userId);
    }).isInstanceOf(UserNotFoundException.class).hasMessage("User with id 1 not found");

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  @DisplayName("Should be able to update a user")
  void updateUser() {
    Long userId = 1L;
    UserPersist userToBeUpdate = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "12345678", true,
        Role.COMMON, null, null);
    UserUpdateDTO userUpdateData = new UserUpdateDTO("Marcos", "marcos@gmail.com", "87654321",
        null);
    UserPersist updatedUser = new UserPersist(userId, "Marcos", "marcos@gmail.com",
        "newHashedPassword",
        true,
        Role.COMMON, null, null);
    UserResponseDTO updatedUserResponse = new UserResponseDTO(userId, "Marcos", "marcos@gmail.com",
        Role.COMMON, true);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userToBeUpdate));
    when(userMapper.toDomain(userToBeUpdate)).thenReturn(domainUser);
    when(passwordEncoder.encode(userUpdateData.password())).thenReturn("newHashedPassword");
    when(userMapper.toPersist(domainUser)).thenReturn(updatedUser);
    when(userMapper.toResponseDTO(updatedUser)).thenReturn(updatedUserResponse);

    UserResponseDTO response = service.update(userId, userUpdateData);

    assertNotNull(response);
    assertThat(response.name()).isEqualTo("Marcos");
    assertThat(response.email()).isEqualTo("marcos@gmail.com");

    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(1)).encode(any(String.class));
    verify(userMapper, times(1)).toDomain(any(UserPersist.class));
    verify(userMapper, times(1)).toPersist(any(User.class));
    verify(userMapper, times(1)).toResponseDTO(any(UserPersist.class));

  }

  @Test
  @DisplayName("Should not be able to update a unexist user")
  void updateUnexistUser() {
    Long userId = 1L;
    UserUpdateDTO userUpdateData = new UserUpdateDTO("Marcos", "marcos@gmail.com", "87654321",
        null);

    when(userRepository.findById(1L)).thenThrow(new UserNotFoundException(userId));

    assertThatThrownBy(() -> {
      service.update(userId, userUpdateData);
    }).isInstanceOf(UserNotFoundException.class).hasMessage("User with id 1 not found");

    verify(userRepository, times(1)).findById(userId);
    verify(verifyUserByEmail, times(0)).execute(any());
    verify(userMapper, times(0)).toPersist(any());
    verify(userMapper, times(0)).toDomain(any());
    verify(userMapper, times(0)).toResponseDTO(any(UserPersist.class));
    verify(passwordEncoder, times(0)).encode(any());

  }

  @Test
  @DisplayName("Should not be able to update a user with an existing email")
  void updateUserWithExistingEmail() {
    Long userId = 1L;
    UserPersist userToBeUpdate = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "12345678", true,
        Role.COMMON, null, null);
    UserUpdateDTO userUpdateData = new UserUpdateDTO("Marcos", "marcos@gmail.com", "87654321",
        null);
    when(userRepository.findById(userId)).thenReturn(Optional.of(userToBeUpdate));
    when(userMapper.toDomain(userToBeUpdate)).thenReturn(domainUser);
    doThrow(new EmailAlreadyExistsException(userUpdateData.email())).when(verifyUserByEmail)
        .execute(userUpdateData.email());

    assertThatThrownBy(() -> {
      service.update(userId, userUpdateData);
    }).isInstanceOf(EmailAlreadyExistsException.class)
        .hasMessage("User with e-mail marcos@gmail.com already exist");

    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(0)).encode(any(String.class));
    verify(userMapper, times(1)).toDomain(any(UserPersist.class));
    verify(userMapper, times(0)).toPersist(any(User.class));
    verify(userMapper, times(0)).toResponseDTO(any(UserPersist.class));

  }

  @Test
  @DisplayName("Should be able to delete a user by id")
  void deleteUser() {
    Long userId = 1L;

    UserPersist userToDelete = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));
    doNothing().when(userRepository).deleteById(userId);

    service.delete(userId);

    verify(userRepository, times(1)).deleteById(userId);
  }

  @Test
  @DisplayName("Should not be able to delete a unexist user by id")
  void deleteUnexistingUser() {
    Long userId = 1L;

    when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));

    assertThatThrownBy(() -> {
      service.delete(userId);
    }).isInstanceOf(UserNotFoundException.class).hasMessage("User with id 1 not found");

    verify(userRepository, times(0)).deleteById(userId);
  }
}

