package com.rafaelw.financeControl.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @Test
  @DisplayName("Should be able create a category")
  void createCategory() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");
    Category category = new Category(user, "Food");

    assertThat(category.getUser()).isEqualTo(user);
    assertThat(category.getName()).isEqualTo("Food");
  }

  @Test
  @DisplayName("Should not be able create a category without a user")
  void createCategoryWithoutUser() {
    assertThatThrownBy(() -> {
      new Category(null, "Food");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("A user is required");
  }

  @Test
  @DisplayName("Should not be able create a category with a invalid name")
  void createCategoryWithoutName() {
    User user = User.create("Joel", "joel@gmail.com", "12345678");

    assertThatThrownBy(() -> {
      new Category(user, "");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");
  }
  
}