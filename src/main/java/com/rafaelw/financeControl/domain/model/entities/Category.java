package com.rafaelw.financeControl.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

  private Long id;
  private String name;

  private Long userId;

  public Category(Long userId, String name) {
    if (userId == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    this.userId = userId;
    this.name = name;
  }

  public void changeName(String name) {
    if (this.name.equals(name)) {
      return;
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    this.name = name;
  }

}
