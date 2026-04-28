package com.rafaelw.financeControl.infra.outbound.persistence.repository;

import com.rafaelw.financeControl.infra.outbound.persistence.entities.UserPersist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserPersist, Long>{
    Optional<UserPersist> findByEmail(String email);
    boolean existsByEmail(String email);
}
