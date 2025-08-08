package com.rafaelw.financeControl.domain.service;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserCreateCategory {

  public Category execute(User user, String name) {
    Category category = new Category();
    category.setUser(user);
    category.setName(name);
    return category;
  }

}
