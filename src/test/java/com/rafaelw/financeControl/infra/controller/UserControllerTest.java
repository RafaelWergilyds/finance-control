package com.rafaelw.financeControl.infra.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.rafaelw.financeControl.application.dto.auth.AuthenticationRequest;
import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.config.DBContainer;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

public class UserControllerTest extends DBContainer {

  @Autowired
  private JpaUserRepository userRepository;

  @Test
  @Sql("/clean.sql")
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
            "id", equalTo(2),
            "email", is("maria@gmail.com")
        );
  }

  @Test
  @Sql("/clean.sql")
  @DisplayName("Should be able to get user profile")
  void getUserProfile() {
    AuthenticationRequest login = new AuthenticationRequest("joel@gmail.com", "12345678");

    String token = given().contentType(ContentType.JSON).body(login).when().post("/auth/login")
        .then().statusCode(200).extract().path("accessToken");

    given().header("Authorization", "Bearer " + token).get("/users/profile").then().statusCode(200)
        .body("email", is("joel@gmail.com"));
  }
}
