package com.rafaelw.financeControl.domain.model.entities;

import java.math.BigDecimal;
import java.time.Instant;
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
public class Debit{

  private Long id;
  private String name;
  private BigDecimal amount;
  private Instant moment;

  private Long userId;
  private Long categoryId;

  public Debit(Long userId, String name, BigDecimal amount) {
    if (userId == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount is required");
    }
    this.userId = userId;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
  }

  public Debit(Long userId, String name, BigDecimal amount, Long categoryId) {
    if (userId == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount is required");
    }
    if (categoryId == null) {
      throw new IllegalArgumentException("A category is required");
    }

    this.userId = userId;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
    this.categoryId = categoryId;
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

  public void changeAmount(BigDecimal amount) {
    if (this.amount.equals(amount)) {
      return;
    }
    if (amount == null || name.isBlank()) {
      throw new IllegalArgumentException("Amount is required");
    }
    this.amount = amount;
  }


}
