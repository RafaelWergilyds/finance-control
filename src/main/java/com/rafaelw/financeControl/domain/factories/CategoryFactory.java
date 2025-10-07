package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

  public Category create(User user, String name) {
    return new Category(user, name);
  }
}
