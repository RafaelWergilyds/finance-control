package com.rafaelw.financeControl.application.mappers.struct;

import com.rafaelw.financeControl.application.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapperStruct {

  CategoryResponseDTO toResponseDTO(CategoryPersist categoryPersist);

  CategoryResponseDTO toResponseDTO(Category category);

  Category toDomain(CategoryPersist categoryPersist, @Context AvoidContext context);

  CategoryPersist toPersist(Category category, @Context AvoidContext context);


}
