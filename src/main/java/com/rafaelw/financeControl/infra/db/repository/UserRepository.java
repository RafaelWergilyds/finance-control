package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
