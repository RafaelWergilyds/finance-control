package com.rafaelw.financeControl.infra.inbound.rest.dto.category;

import com.rafaelw.financeControl.domain.model.entities.Category;

public record CategoryResponseDTO(Long id, String name) {

    public static CategoryResponseDTO fromDomain(Category category){
        return new CategoryResponseDTO(category.getId(), category.getName());
    }

}
