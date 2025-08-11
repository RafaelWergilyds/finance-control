package com.rafaelw.financeControl.infra.mappers.struct;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.db.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.dto.category.CategoryResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapperStruct {

  CategoryResponseDTO toResponseDTO(CategoryPersist categoryPersist);

  CategoryResponseDTO toResponseDTO(Category category);

  Category toDomain(CategoryPersist categoryPersist, @Context AvoidContext context);

  CategoryPersist toPersist(Category category, @Context AvoidContext context);


}
