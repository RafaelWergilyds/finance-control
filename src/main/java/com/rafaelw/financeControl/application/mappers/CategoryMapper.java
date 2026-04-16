package com.rafaelw.financeControl.application.mappers;

import com.rafaelw.financeControl.application.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  @Autowired
  private JpaUserRepository userRepository;

  public CategoryResponseDTO toResponseDTO(CategoryPersist categoryPersist) {
    return new CategoryResponseDTO(categoryPersist.getId(), categoryPersist.getName());
  }

  public Category toDomain(CategoryPersist categoryPersist) {
    Category category = new Category();

    category.setId(categoryPersist.getId());
    category.setUserId(categoryPersist.getUser().getId());
    category.setName(categoryPersist.getName());
    return category;
  }

  public CategoryPersist toPersist(Category category) {
    CategoryPersist categoryPersist = new CategoryPersist();

    categoryPersist.setId(category.getId());
    categoryPersist.setName(category.getName());

    if(category.getUserId() != null){
      UserPersist userPersist = new UserPersist();
      userPersist.setId(category.getUserId());
      categoryPersist.setUser(userPersist);
    }

    return categoryPersist;
  }

}
