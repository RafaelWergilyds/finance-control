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
    Long userId = 1L;
    Long categoryId = 2L;

    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00), categoryId);

    assertThat(debit.getUserId()).isEqualTo(userId);
    assertThat(debit.getName()).isEqualTo("Pizza");
    assertThat(debit.getCategoryId()).isEqualTo(categoryId);
  }

  @Test
  @DisplayName("Should be able create a debit without a category")
  void createDebitWithoutCategory() {
    Long userId = 1L;
    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00));

    assertThat(debit.getUserId()).isEqualTo(userId);
    assertThat(debit.getName()).isEqualTo("Pizza");
    assertThat(debit.getCategoryId()).isNull();
  }

  @Test
  @DisplayName("Should not be able create a debit with a invalid name")
  void createDebitWithoutName() {
    Long userId = 1L;

    assertThatThrownBy(() -> {
      new Debit(userId, "", BigDecimal.valueOf(50.00));
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
    Long userId = 1L;

    assertThatThrownBy(() -> {
      new Debit(userId, "Pizza", null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Amount is required");

  }

  @Test
  @DisplayName("Should be able to change debit name")
  void changeDebitName() {
    Long userId = 1L;
    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00));
    debit.changeName("Pastel");

    assertThat(debit.getName()).isEqualTo("Pastel");

  }

  @Test
  @DisplayName("Should not be able to change a debit name with a invalid name")
  void changeDebitNameWithInvalidName() {
    Long userId = 1L;
    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00));

    assertThatThrownBy(() -> {
      debit.changeName("");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");

  }

  @Test
  @DisplayName("Should be able to change debit amount")
  void changeDebitAmount() {
    Long userId = 1L;
    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00));
    debit.changeAmount(BigDecimal.valueOf(30.00));

    assertThat(debit.getAmount()).isEqualTo(BigDecimal.valueOf(30.00));

  }

  @Test
  @DisplayName("Should not be able to change debit amount with invalid amount")
  void changeDebitAmountWithInvalidAmount() {
    Long userId = 1L;
    Debit debit = new Debit(userId, "Pizza", BigDecimal.valueOf(50.00));
    
    assertThatThrownBy(() -> {
      debit.changeAmount(null);
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Amount is required");
  }

}
