package com.rafaelw.financeControl.application.service;

import com.rafaelw.financeControl.application.ports.in.UserService;
import com.rafaelw.financeControl.application.ports.out.UserRepositoryPort;
import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.domain.factories.UserFactory;

import java.util.List;
import java.util.Optional;

import com.rafaelw.financeControl.domain.services.exceptions.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private final UserRepositoryPort userRepositoryPort;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepositoryPort userRepositoryPort) {
    this.userRepositoryPort = userRepositoryPort;
  }

  @Transactional(readOnly = true)
  public List<User> findAll() {
    return userRepositoryPort.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<User> findById(Long id) {
    return userRepositoryPort.findById(id);
  }

  @Transactional
  public User create(String name, String email, String password) {
    verifyEmailAlreadyExists(email);

    User user = UserFactory.create(name, email, password, passwordEncoder);

    return userRepositoryPort.save(user);
  }

  @Transactional
  public Optional<User> update(Long id, String name, String email, String password) {
      return userRepositoryPort.findById(id).map(user -> {
        if (name != null) {
          user.changeName(name);
        }
        if (email != null) {
          verifyEmailAlreadyExists(email);
          user.changeEmail(email);
        }
        if (password != null) {
          user.changePassword(passwordEncoder.encode(password));
        }

        return userRepositoryPort.save(user);
      });

  }

  @Transactional
  public void delete(Long id) {
    userRepositoryPort.findById(id).ifPresent(user -> {
      userRepositoryPort.delete(id);
    });
  }

  private void verifyEmailAlreadyExists(String email){
    if(userRepositoryPort.existsByEmail(email)){
      throw new EmailAlreadyExistsException(email);
    }
  }

}
