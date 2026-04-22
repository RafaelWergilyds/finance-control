package com.rafaelw.financeControl.application.dto.user;

import com.rafaelw.financeControl.domain.model.entities.enums.Role;

public record UserResponseDTO(Long id, String name, String email, Role role, boolean active) {

}
