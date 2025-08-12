package com.rafaelw.financeControl.application.dto.user;

import com.rafaelw.financeControl.domain.entities.enums.Role;

public record UserResponseDTO(Long id, String name, String email, Role role, boolean active) {

}
