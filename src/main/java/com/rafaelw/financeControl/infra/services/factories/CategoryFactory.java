package com.rafaelw.financeControl.infra.services.factories;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.UserCreateCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

  @Autowired
  private UserCreateCategory userCreateCategory;

  public Category create(User user, String name) {
    return userCreateCategory.execute(user, name);
  }
}
