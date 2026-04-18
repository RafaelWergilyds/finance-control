package com.rafaelw.financeControl.domain.entities;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.domain.valueObjects.Email;
import com.rafaelw.financeControl.domain.valueObjects.Password;
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
public class User{

  private Long id;
  private String name;
  private Email email;
  private Password password;
  private boolean active = true;

  private Role role;

  public User(String name, Email email, Password password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = Role.COMMON;
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
    if (this.email != null && this.email.email().equals(email)){
      return;
    }

    this.email = new Email(email);
  }

  public String getEmail(){
    return this.email.email();
  }

  public String getPassword(){
    return this.password.hashedPassword();
  }

  public void changePassword(String newPassword) {
    this.password = new Password(newPassword);
  }


  public void activateUser() {
    this.active = true;
  }

  public void deactivateUser() {
    this.active = false;
  }
}
