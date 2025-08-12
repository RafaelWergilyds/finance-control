package com.rafaelw.financeControl.infra.persist.repository;

import com.rafaelw.financeControl.domain.repository.UserRepository;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserPersist, Long>, UserRepository {

}
