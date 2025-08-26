package com.rafaelw.financeControl.application.dto.auth;

public record AuthenticationResponse(String accessToken, Long expiresIn) {

}

