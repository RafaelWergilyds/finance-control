package com.rafaelw.financeControl.infra.services.factories;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  @Autowired
  private JpaUserRepository repository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private VerifyUserByEmail verifyUserByEmail;

  public User create(String name, String email, String password, Role role){
    verifyUserByEmail.execute(email);
    return User.create(name, email, password, role);
  }
}
