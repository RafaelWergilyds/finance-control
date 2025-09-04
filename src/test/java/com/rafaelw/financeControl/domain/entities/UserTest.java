package com.rafaelw.financeControl.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  @DisplayName("Should be create a domain user successfully")
  void createUser() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThat(user.getName()).isEqualTo("Joel");
    assertThat(user.getEmail()).isEqualTo("joel@gmail.com");
    assertThat(user.getRole()).isEqualTo(Role.COMMON);
  }

  @Test
  @DisplayName("Should not be able to create a user with invalid name")
  void createUserWithNameInvalid() {
    assertThatThrownBy(() -> {
      User.create("", "joel@gmail.com", "12345678");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");
  }

  @Test
  @DisplayName("Should not be able to create a user with invalid email")
  void createUserWithEmailInvalid() {
    assertThatThrownBy(() -> {
      User.create("Joel", "joelgmail.com", "12345678");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid email");
  }

  @Test
  @DisplayName("Should not be able to create a user with invalid password")
  void createUserWithPasswordInvalid() {
    assertThatThrownBy(() -> {
      User.create("Joel", "joel@gmail.com", "");
    }).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Password must be at least 8 characters long");
  }

  @Test
  @DisplayName("Should be able to change user name")
  void changeUserName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    user.setName("John");

    assertThat(user.getName()).isEqualTo("John");
  }


  @Test
  @DisplayName("Should not be able to change user name with invalid name")
  void changeUserNameWithInvalidName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThatThrownBy(() -> {
      user.changeName("");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");
  }

  @Test
  @DisplayName("Should be able to change user email")
  void changeUserEmail() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    user.changeEmail("joel2@gmail.com");

    assertThat(user.getEmail()).isEqualTo("joel2@gmail.com");
  }

  @Test
  @DisplayName("Should not be able to change user name with invalid name")
  void changeUserEmailWithInvalidEmail() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThatThrownBy(() -> {
      user.changeEmail("");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid email");
  }

  @Test
  @DisplayName("Should be able to change user password")
  void changeUserPassword() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    user.changePassword("87654321");

    assertThat(user.getPassword()).isEqualTo("87654321");
  }

  @Test
  @DisplayName("Should not be able to change user password with invalid password")
  void changeUserPasswordWithInvalidPassword() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    assertThatThrownBy(() -> {
      user.changePassword("");
    }).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Password must be at least 8 characters long");
  }

  @Test
  @DisplayName("Should be able to change the status active user to true")
  void activateUser() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    user.deactivateUser();
    user.activateUser();

    assertThat(user.isActive()).isEqualTo(true);
  }

  @Test
  @DisplayName("Should be able to change the status active user to false")
  void deactivateUser() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    user.deactivateUser();

    assertThat(user.isActive()).isEqualTo(false);
  }
}