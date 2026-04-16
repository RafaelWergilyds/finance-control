package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

  public Category create(Long userId, String name) {
    return new Category(userId, name);
  }
}
