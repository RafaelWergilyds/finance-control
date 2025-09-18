package com.rafaelw.financeControl.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AuthenticationControllerTest {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("Should be to authenticate successfully with valid credentials")
  void authenticate() {
    AuthenticationRequest authRequest = new AuthenticationRequest("john@gmail.com", "12345678");

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
