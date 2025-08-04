package com.rafaelw.financeControl;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceControlApplication.class, args);


		User user1 = new User(1L, "jorge", "test@gmail.com", "123456", Role.COMMON, null, null);
		Category category1 = new Category(1L, "Comida", user1, null);
		Category category2 = new Category(2L, "Saúde", user1, null);

		Debit debit = new Debit(1L, "pizza", new BigDecimal("50"), Instant.now(),user1, category1);

		System.out.println(debit);
	}

}
