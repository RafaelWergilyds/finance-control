package com.rafaelw.financeControl.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DebitTest {

  @Test
  @DisplayName("Should be able create a debit with a category")
  void createDebitWithCategory() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    Category category = new Category(user, "Food");

    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00), category);

    assertThat(debit.getUser()).isEqualTo(user);
    assertThat(debit.getName()).isEqualTo("Pizza");
    assertThat(debit.getCategory()).isEqualTo(category);
  }

  @Test
  @DisplayName("Should be able create a debit without a category")
  void createDebitWithoutCategory() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00));

    assertThat(debit.getUser()).isEqualTo(user);
    assertThat(debit.getName()).isEqualTo("Pizza");
    assertThat(debit.getCategory()).isEqualTo(null);
  }

  @Test
  @DisplayName("Should not be able create a debit with a invalid name")
  void createDebitWithoutName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThatThrownBy(() -> {
      new Debit(user, "", BigDecimal.valueOf(50.00));
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");

  }

  @Test
  @DisplayName("Should not be able create a debit without a user")
  void createDebitWithoutUser() {

    assertThatThrownBy(() -> {
      new Debit(null, "Pizza", BigDecimal.valueOf(50.00));
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("A user is required");

  }

  @Test
  @DisplayName("Should not be able create a debit with a invalid amount")
  void createDebitWithoutAmount() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThatThrownBy(() -> {
      new Debit(user, "Pizza", null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Amount is required");

  }

  @Test
  @DisplayName("Should be able to change debit name")
  void changeDebitName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    Category category = new Category(user, "Food");

    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00), category);
    debit.changeName("Pastel");

    assertThat(debit.getName()).isEqualTo("Pastel");

  }

  @Test
  @DisplayName("Should not be able to change a debit name with a invalid name")
  void changeDebitNameWithInvalidName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00));

    assertThatThrownBy(() -> {
      debit.changeName("");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");

  }

  @Test
  @DisplayName("Should be able to change debit amount")
  void changeDebitAmount() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00));
    debit.changeAmount(BigDecimal.valueOf(30.00));

    assertThat(debit.getAmount()).isEqualTo(BigDecimal.valueOf(30.00));

  }

  @Test
  @DisplayName("Should not be able to change debit amount with invalid amount")
  void changeDebitAmountWithInvalidAmount() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    Debit debit = new Debit(user, "Pizza", BigDecimal.valueOf(50.00));
    assertThatThrownBy(() -> {
      debit.changeAmount(null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Amount is required");


  }


}