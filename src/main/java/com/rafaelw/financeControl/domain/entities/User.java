package com.rafaelw.financeControl.domain.entities;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class User {
  private Long id;
  private String name;
  private String email;
  private String password;

  private Role role;
  private Set<Category> categories = new HashSet<>();
  private List<Debit> debits = new ArrayList<>();

  public void addCategory(Category category){
      this.categories.add(category);
      category.setUser(this);
  }

  public void removeCategory(Category category){
    this.categories.remove(category);
    category.setUser(null);
  }

  public void addDebit(Debit debit){
    this.debits.add(debit);
    debit.setUser(this);
  }
}
