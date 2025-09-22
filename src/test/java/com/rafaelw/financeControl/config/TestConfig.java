package com.rafaelw.financeControl.config;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestConfig {

  @Autowired
  private JpaUserRepository userRepository;
  @Autowired
  private JpaCategoryRepository categoryRepository;
  @Autowired
  private JpaDebitRepository debitRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public void setup() {
    UserPersist user = new UserPersist(null, "Joel", "joel@gmail.com",
        passwordEncoder.encode("12345678"), true, Role.ADMIN,
        null, null);

    UserPersist user2 = new UserPersist(null, "Lucas", "lucas@gmail.com",
        passwordEncoder.encode("12345678"), true, Role.ADMIN,
        null, null);

    CategoryPersist category = new CategoryPersist(null, "Food", user, null);
    CategoryPersist category2 = new CategoryPersist(null, "Monthly", user, null);

    List<DebitPersist> debits = IntStream.range(0, 10)
        .mapToObj(i -> new DebitPersist(
            null,
            "Pizza",
            BigDecimal.valueOf(50),
            Instant.now(),
            user,
            category
        )).toList();

    List<DebitPersist> debits2 = IntStream.range(0, 5)
        .mapToObj(i -> new DebitPersist(
            null,
            "Bills",
            BigDecimal.valueOf(20),
            Instant.now(),
            user,
            category2
        )).toList();

    userRepository.save(user);
    userRepository.save(user2);
    categoryRepository.save(category);
    categoryRepository.save(category2);
    debitRepository.saveAll(debits);
    debitRepository.saveAll(debits2);
  }

}
