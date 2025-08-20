package com.rafaelw.financeControl.infra.config;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private JpaCategoryRepository categoryRepository;

  @Autowired
  private JpaDebitRepository debitRepository;

  @Override
  public void run(String... args) throws Exception {
    UserPersist user = new UserPersist(null, "John", "john@gmail.com", "123456", true, Role.COMMON,
        null, null);

    CategoryPersist category = new CategoryPersist(null, "Comida", user, null);

    DebitPersist debit1 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit2 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit3 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit4 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit5 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit6 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit7 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit8 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit9 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit10 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit11 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit12 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit13 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit14 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit15 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit16 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit17 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit18 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit19 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit20 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit21 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit22 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit23 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit24 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit25 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit26 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit27 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit28 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit29 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    DebitPersist debit30 = new DebitPersist(null, "Pizza", BigDecimal.valueOf(50), Instant.now(),
        user,
        category);

    userRepository.save(user);
    categoryRepository.save(category);
    debitRepository.saveAll(
        Arrays.asList(debit1, debit2, debit3, debit4, debit5, debit6, debit7, debit8, debit9,
            debit10, debit11, debit12, debit13, debit14, debit15, debit16, debit17, debit18,
            debit19, debit20, debit21, debit22, debit23, debit24, debit25, debit26, debit27,
            debit28,
            debit29, debit30));


  }

}
