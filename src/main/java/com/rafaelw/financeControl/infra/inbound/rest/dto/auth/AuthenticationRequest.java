package com.rafaelw.financeControl.infra.inbound.rest.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank @Email String email, @NotBlank String password) {

}
