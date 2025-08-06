package com.rafaelw.financeControl.infra.mappers;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  @Mapping(target = "user.categories", ignore = true)
  @Mapping(target = "user.debits", ignore = true)
  @Mapping(target = "debits", ignore = true)
  @Mapping(target = "user", ignore = true)
  Category toCategory(CategoryEntity categoryEntity);

  @Mapping(target = "user.categories", ignore = true)
  @Mapping(target = "user.debits", ignore = true)
  @Mapping(target = "debits", ignore = true)
  @Mapping(target = "user", ignore = true)
  CategoryEntity toCategoryEntity(Category category);


}
