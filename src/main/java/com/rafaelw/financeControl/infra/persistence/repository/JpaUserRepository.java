package com.rafaelw.financeControl.infra.persistence.repository;

import com.rafaelw.financeControl.domain.repository.UserRepository;
import com.rafaelw.financeControl.infra.persistence.entities.UserPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserPersist, Long>, UserRepository {

}
