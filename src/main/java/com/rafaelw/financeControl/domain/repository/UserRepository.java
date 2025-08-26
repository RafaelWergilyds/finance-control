package com.rafaelw.financeControl.domain.repository;

import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import java.util.Optional;

public interface UserRepository {

  boolean existsByEmail(String email);

  Optional<UserPersist> findByEmail(String email);
}
