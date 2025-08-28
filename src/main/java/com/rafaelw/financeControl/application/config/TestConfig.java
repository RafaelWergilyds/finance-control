package com.rafaelw.financeControl.application.config;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private JpaCategoryRepository categoryRepository;

  @Autowired
  private JpaDebitRepository debitRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    UserPersist user = new UserPersist(null, "John", "john@gmail.com",
        passwordEncoder.encode("123456"), true, Role.ADMIN,
        null, null);

    CategoryPersist category = new CategoryPersist(null, "Comida", user, null);

    List<DebitPersist> debits = IntStream.range(0, 100)
        .mapToObj(i -> new DebitPersist(
            null,
            "Pizza",
            BigDecimal.valueOf(50),
            Instant.now(),
            user,
            category
        )).toList();

    userRepository.save(user);
    categoryRepository.save(category);
    debitRepository.saveAll(debits);


  }

}
