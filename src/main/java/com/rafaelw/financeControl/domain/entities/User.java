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
  private boolean active;

  private Role role;
  private Set<Category> categories = new HashSet<>();
  private List<Debit> debits = new ArrayList<>();

  public User(String name, String email, String password, Role role){
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.active = true;
  }

  public static User create(String name, String email, String password, Role role){
    return new User(name, email, password, role);
  }
}
