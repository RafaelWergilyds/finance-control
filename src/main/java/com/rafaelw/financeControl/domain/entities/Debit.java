package com.rafaelw.financeControl.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Debit extends AbstractAggregateRoot<Debit>, Serializable {

  private Long id;
  private String name;
  private BigDecimal amount;
  private Instant moment;

  private User user;
  private Category category;

  public Debit(User user, String name, BigDecimal amount) {
    if (user == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount is required");
    }
    this.user = user;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
  }

  public Debit(User user, String name, BigDecimal amount, Category category) {
    if (user == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (amount == null) {
      throw new IllegalArgumentException("Amount is required");
    }
    if (category == null) {
      throw new IllegalArgumentException("A category is required");
    }

    this.user = user;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
    this.category = category;
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
