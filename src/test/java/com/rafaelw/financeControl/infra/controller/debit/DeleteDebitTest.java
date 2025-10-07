package com.rafaelw.financeControl.infra.controller.debit;

import static io.restassured.RestAssured.given;

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

public class DeleteDebitTest extends DBContainer {

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
  @DisplayName("Should be able to delete a debit")
  void delete() {
    AuthenticationRequest login = new AuthenticationRequest("joel@gmail.com", "12345678");

    String token = given().contentType(ContentType.JSON).body(login).when().post("/auth/login")
        .then().statusCode(200).extract().path("accessToken");

    given()
        .header("Authorization", "Bearer " + token)
        .delete("/debits/1").then().statusCode(204);
  }
}
