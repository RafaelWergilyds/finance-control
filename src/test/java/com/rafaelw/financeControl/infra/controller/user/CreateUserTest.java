package com.rafaelw.financeControl.infra.controller.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.config.DBContainer;
import com.rafaelw.financeControl.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

public class CreateUserTest extends DBContainer {

  @LocalServerPort
  private int port;

  @Autowired
  private TestConfig testConfig;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    testConfig.setup();
  }

  @Test
  @DisplayName("Should be able to create a user")
  void create() {
    UserRequestDTO createRequest = new UserRequestDTO("Maria", "maria@gmail.com", "12345678");

    given()
        .contentType(ContentType.JSON)
        .body(createRequest)
        .when()
        .post("/users")
        .then()
        .statusCode(201)
        .body(
            "id", equalTo(3),
            "email", is("maria@gmail.com")
        );
  }
}
