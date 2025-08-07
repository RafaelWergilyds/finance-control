package com.rafaelw.financeControl.infra.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
    @NotBlank(message = "name must be not blank")
    String name,

    @NotBlank(message = "email must be not blank")
    @Email(message = "email format invalid")
    String email,

    @NotBlank
    String password
)
{
}
