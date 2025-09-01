package com.rafaelw.financeControl.domain.entities;

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

  public Category(User user, String name) {
    if (user == null) {
      throw new IllegalArgumentException("A user is required");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    this.user = user;
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
