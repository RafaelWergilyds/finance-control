package com.rafaelw.financeControl.domain.entities;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class User extends AbstractAggregateRoot<User> {

  private Long id;
  private String name;
  private String email;
  private String password;
  private boolean active = true;

  private Role role;
  private Set<Category> categories = new HashSet<>();
  private List<Debit> debits = new ArrayList<>();

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = Role.COMMON;
  }

  public static User create(String name, String email, String password) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Invalid email");
    }
    if (password == null || password.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    return new User(name, email, password);
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

  public void changeEmail(String email) {
    if (this.email.equals(email)) {
      return;
    }
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Invalid email");
    }

    this.email = email;
  }

  public void changePassword(String password) {
    if (password == null || password.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long");
    }
    this.password = password;
  }

  public void activateUser() {
    this.active = true;
  }

  public void deactivateUser() {
    this.active = false;
  }
}
