package com.rafaelw.financeControl.domain.service;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import org.springframework.stereotype.Service;

@Service
public class CreateCategory {

  public Category createCategory(User user, String name){
    Category category = new Category();
    category.setUser(user);
    category.setName(name);

    return category;
  }

}
