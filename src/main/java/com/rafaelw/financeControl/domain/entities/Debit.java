package com.rafaelw.financeControl.domain.entities;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = "id")
public class Debit {

  private Long id;
  private String name;
  private BigDecimal amount;
  private Instant moment;

  private User user;
  private Category category;

  public Debit(User user, String name, BigDecimal amount) {
    this.user = user;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
  }

  public Debit(User user, String name, BigDecimal amount, Category category) {
    this.user = user;
    this.name = name;
    this.amount = amount;
    this.moment = Instant.now();
    this.category = category;
  }


}
