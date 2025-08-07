package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.domain.repository.UserRepository;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, UserRepository {
}
