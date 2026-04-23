package com.rafaelw.financeControl.application.ports.out;

import com.rafaelw.financeControl.domain.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
    void delete(Long id);

}
