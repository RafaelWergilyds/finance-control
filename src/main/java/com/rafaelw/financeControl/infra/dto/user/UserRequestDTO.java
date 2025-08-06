package com.rafaelw.financeControl.infra.dto.user;

import com.rafaelw.financeControl.domain.entities.enums.Role;

public record UserRequestDTO(String name, String email, String password, Role role) {

}
