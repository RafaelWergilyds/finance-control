package com.rafaelw.financeControl.infra.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.config.DBContainer;
import com.rafaelw.financeControl.config.TestConfig;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationControllerTest extends DBContainer {

  @LocalServerPort
  private int port;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JpaUserRepository userRepository;
  
  @Autowired
  private TestConfig testConfig;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    testConfig.setup();
  }

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
