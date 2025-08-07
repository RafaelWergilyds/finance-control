package com.rafaelw.financeControl.domain.repository;

public interface UserRepository {
  boolean existsByEmail(String email);
}
