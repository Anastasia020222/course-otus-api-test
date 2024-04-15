package services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class AllureRestAPI {

  @BeforeAll
  public static void allure() {
    RestAssured.filters(new AllureRestAssured());
  }
}
