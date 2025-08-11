package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.db.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.infra.mappers.struct.AvoidContext;
import com.rafaelw.financeControl.infra.mappers.struct.CategoryMapperStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  @Autowired
  private CategoryMapperStruct mapperStruct;

  public CategoryResponseDTO toResponseDTO(Category category) {
    return mapperStruct.toResponseDTO(category);
  }

  public CategoryResponseDTO toResponseDTO(CategoryPersist categoryPersist) {
    return mapperStruct.toResponseDTO(categoryPersist);
  }

  public Category toDomain(CategoryPersist categoryPersist) {
    return mapperStruct.toDomain(categoryPersist, new AvoidContext());
  }

  public CategoryPersist toPersist(Category category) {
    return mapperStruct.toPersist(category, new AvoidContext());
  }

}
