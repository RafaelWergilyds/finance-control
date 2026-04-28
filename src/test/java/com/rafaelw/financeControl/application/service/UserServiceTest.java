package com.rafaelw.financeControl.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafaelw.financeControl.application.ports.out.UserRepositoryPort;
import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.domain.model.entities.enums.Role;
import com.rafaelw.financeControl.domain.model.valueObjects.Email;
import com.rafaelw.financeControl.domain.model.valueObjects.Password;
import com.rafaelw.financeControl.domain.services.exceptions.EmailAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

class UserServiceTest {

  @Mock
  private UserRepositoryPort userRepositoryPort;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl service;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(service, "passwordEncoder", passwordEncoder);
  }

  @Test
  @DisplayName("Should be able create a user")
  void createUser() {
    String name = "Joel";
    String email = "joel@gmail.com";
    String password = "password";

    when(userRepositoryPort.existsByEmail(email)).thenReturn(false);
    when(userRepositoryPort.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User createdUser = service.create(name, email, password);

    assertThat(createdUser).isNotNull();
    assertThat(createdUser.getName()).isEqualTo(name);
    assertThat(createdUser.getEmail()).isEqualTo(email);

    verify(userRepositoryPort, times(1)).existsByEmail(email);
    verify(userRepositoryPort, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("Should not be able create a user with same email")
  void createUserWithSameEmail() {
    String name = "Joel";
    String email = "joel@gmail.com";
    String password = "password";

    when(userRepositoryPort.existsByEmail(email)).thenReturn(true);

    assertThatThrownBy(() -> service.create(name, email, password))
        .isInstanceOf(EmailAlreadyExistsException.class)
        .hasMessage("User with e-mail joel@gmail.com already exist");

    verify(userRepositoryPort, times(1)).existsByEmail(email);
    verify(userRepositoryPort, never()).save(any(User.class));
  }

  @Test
  @DisplayName("Should be able to find all users")
  void findAllUsers() {
    User user1 = User.builder().id(1L).name("Joel").email(new Email("joel@gmail.com")).build();
    User user2 = User.builder().id(2L).name("Maria").email(new Email("maria@gmail.com")).build();
    List<User> users = List.of(user1, user2);

    when(userRepositoryPort.findAll()).thenReturn(users);

    List<User> response = service.findAll();

    assertThat(response).isNotNull();
    assertThat(response).hasSize(2);
    assertThat(response.get(0).getName()).isEqualTo("Joel");
    assertThat(response.get(1).getName()).isEqualTo("Maria");

    verify(userRepositoryPort, times(1)).findAll();
  }

  @Test
  @DisplayName("Should be able to find a user by id")
  void findUserById() {
    Long userId = 1L;
    User user = User.builder().id(userId).name("Joel").email(new Email("joel@gmail.com")).build();

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));

    Optional<User> response = service.findById(userId);

    assertThat(response).isPresent();
    assertThat(response.get().getId()).isEqualTo(userId);

    verify(userRepositoryPort, times(1)).findById(userId);
  }

  @Test
  @DisplayName("Should return empty when finding user with non-existent id")
  void findUnexistUserId() {
    Long userId = 1L;

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

    Optional<User> response = service.findById(userId);

    assertThat(response).isEmpty();

    verify(userRepositoryPort, times(1)).findById(userId);
  }

  @Test
  @DisplayName("Should be able to update a user")
  void updateUser() {
    Long userId = 1L;
    User existingUser = User.builder()
            .id(userId)
            .name("Joel")
            .email(new Email("joel@gmail.com"))
            .password(new Password("hashedPassword"))
            .active(true)
            .role(Role.COMMON)
            .build();
    
    String newName = "Marcos";
    String newEmail = "marcos@gmail.com";
    String newPassword = "newPassword";

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepositoryPort.existsByEmail(newEmail)).thenReturn(false);
    when(passwordEncoder.encode(newPassword)).thenReturn("newHashedPassword");
    when(userRepositoryPort.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<User> response = service.update(userId, newName, newEmail, newPassword);

    assertThat(response).isPresent();
    assertThat(response.get().getName()).isEqualTo(newName);
    assertThat(response.get().getEmail()).isEqualTo(newEmail);
    assertThat(response.get().getPassword()).isEqualTo("newHashedPassword");

    verify(userRepositoryPort, times(1)).findById(userId);
    verify(userRepositoryPort, times(1)).existsByEmail(newEmail);
    verify(userRepositoryPort, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("Should return empty when updating non-existent user")
  void updateUnexistUser() {
    Long userId = 1L;
    when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

    Optional<User> response = service.update(userId, "name", "email@gmail.com", "pass");

    assertThat(response).isEmpty();

    verify(userRepositoryPort, times(1)).findById(userId);
    verify(userRepositoryPort, never()).save(any());
  }

  @Test
  @DisplayName("Should not be able to update a user with an existing email")
  void updateUserWithExistingEmail() {
    Long userId = 1L;
    User existingUser = User.builder()
            .id(userId)
            .name("Joel")
            .email(new Email("joel@gmail.com"))
            .build();
    
    String newEmail = "marcos@gmail.com";

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userRepositoryPort.existsByEmail(newEmail)).thenReturn(true);

    assertThatThrownBy(() -> service.update(userId, null, newEmail, null))
        .isInstanceOf(EmailAlreadyExistsException.class)
        .hasMessage("User with e-mail marcos@gmail.com already exist");

    verify(userRepositoryPort, times(1)).findById(userId);
    verify(userRepositoryPort, times(1)).existsByEmail(newEmail);
    verify(userRepositoryPort, never()).save(any());
  }

  @Test
  @DisplayName("Should be able to delete a user by id")
  void deleteUser() {
    Long userId = 1L;
    User existingUser = User.builder().id(userId).build();

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(existingUser));

    service.delete(userId);

    verify(userRepositoryPort, times(1)).findById(userId);
    verify(userRepositoryPort, times(1)).delete(userId);
  }

  @Test
  @DisplayName("Should not call delete when deleting non-existent user")
  void deleteUnexistingUser() {
    Long userId = 1L;

    when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

    service.delete(userId);

    verify(userRepositoryPort, times(1)).findById(userId);
    verify(userRepositoryPort, never()).delete(any());
  }
}
