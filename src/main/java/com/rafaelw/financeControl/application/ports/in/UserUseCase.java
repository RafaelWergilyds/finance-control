package com.rafaelw.financeControl.application.ports.in;

import com.rafaelw.financeControl.domain.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    User create(String name, String email, String password);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> update(Long id, String name, String email, String password);
    void delete(Long id);

}
