package com.rafaelw.financeControl.infra.dto.user;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
    @Nullable
    String name,

    @Nullable
    @Email(message = "email format invalid")
    String email,

    @Nullable
    String password,

    @Nullable
    Role role) {
}