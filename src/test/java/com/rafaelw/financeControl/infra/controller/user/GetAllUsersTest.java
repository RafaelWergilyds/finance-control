package com.rafaelw.financeControl.infra.controller.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.config.DBContainer;
import com.rafaelw.financeControl.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

public class GetAllUsersTest extends DBContainer {

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
  @DisplayName("Should be able to get all users")
  void getAll() {
    AuthenticationRequest login = new AuthenticationRequest("joel@gmail.com", "12345678");

    String token = given().contentType(ContentType.JSON).body(login).when().post("/auth/login")
        .then().statusCode(200).extract().path("accessToken");

    given().header("Authorization", "Bearer " + token).get("/users").then().statusCode(200)
        .body("$", hasSize(2))
        .body("[0].email", equalTo("joel@gmail.com"))
        .body("[1].email", equalTo("lucas@gmail.com"));
  }
}
