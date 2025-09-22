package com.rafaelw.financeControl.infra.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.config.DBContainer;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationControllerTest extends DBContainer {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JpaUserRepository userRepository;

  @Test
  @DisplayName("Should be to authenticate successfully with valid credentials")
  void authenticate() {
    AuthenticationRequest authRequest = new AuthenticationRequest("joel@gmail.com", "12345678");

    given()
        .contentType(ContentType.JSON)
        .body(authRequest)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .body("accessToken", notNullValue());
  }
}
