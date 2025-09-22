package com.rafaelw.financeControl.infra.controller.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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

public class GetUserProfileTest extends DBContainer {

  @Autowired
  private JpaUserRepository userRepository;

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
  @DisplayName("Should be able to get user profile")
  void getUserProfile() {
    AuthenticationRequest login = new AuthenticationRequest("joel@gmail.com", "12345678");

    String token = given().contentType(ContentType.JSON).body(login).when().post("/auth/login")
        .then().statusCode(200).extract().path("accessToken");

    given().header("Authorization", "Bearer " + token).get("/users/profile").then().statusCode(200)
        .body("email", is("joel@gmail.com"));
  }
}
