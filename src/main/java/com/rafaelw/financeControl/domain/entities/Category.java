package com.rafaelw.financeControl.domain.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class Category {

  private Long id;
  private String name;

  private User user;
  private List<Debit> debits = new ArrayList<>();

  public Category(String name) {
    this.name = name;
  }

  public BigDecimal SumTotalDebits() {
    if (debits == null || debits.isEmpty()) {
      return BigDecimal.ZERO;
    }
    return debits.stream().map(Debit::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
