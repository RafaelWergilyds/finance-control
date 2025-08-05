package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category toCategory(CategoryEntity categoryEntity);
  CategoryEntity toCategoryEntity(Category category);


}
