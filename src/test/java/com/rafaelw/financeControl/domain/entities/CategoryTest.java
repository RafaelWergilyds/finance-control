package com.rafaelw.financeControl.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @Test
  @DisplayName("Should be able create a category")
  void createCategory() {
    Long userId = 1L;
    Category category = new Category(userId, "Food");

    assertThat(category.getUserId()).isEqualTo(userId);
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
    Long userId = 1L;

    assertThatThrownBy(() -> {
      new Category(userId, "");
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Name is required");
  }

  @Test
  @DisplayName("Should be able to change category name")
  void changeCategoryName() {
    Long userId = 1L;
    Category category = new Category(userId, "Food");
    category.changeName("Transport");

    assertThat(category.getName()).isEqualTo("Transport");
  }

}
