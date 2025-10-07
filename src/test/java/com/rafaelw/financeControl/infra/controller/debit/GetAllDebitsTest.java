package com.rafaelw.financeControl.infra.controller.debit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

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

public class GetAllDebitsTest extends DBContainer {

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
  @DisplayName("Should be able to find all user debits")
  void getAll() {
    AuthenticationRequest login = new AuthenticationRequest("joel@gmail.com", "12345678");

    String token = given().contentType(ContentType.JSON).body(login).when().post("/auth/login")
        .then().statusCode(200).extract().path("accessToken");

    given().header("Authorization", "Bearer " + token)
        .get("/debits").then().statusCode(200)
        .header("X-Previous-Page", nullValue())
        .header("X-Next-Page", is("5"))
        .header("X-Start-Cursor", is("15"))
        .header("X-End-Cursor", is("6"))
        .body("$", hasSize(10), "[0].name", is("Bills"));
  }
}
