package com.rafaelw.financeControl.infra.inbound.rest.dto.user;

import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.domain.model.entities.enums.Role;

public record UserResponseDTO(Long id, String name, String email, Role role, boolean active) {

    public static UserResponseDTO fromDomain(User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isActive());
    }

}
