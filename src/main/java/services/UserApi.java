package services;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class UserApi {

  private static final String BASE_URL = System.getProperty("wiremock.url");
  private static final String GET_USER_PATH = "/user/get/all";

  private static final String COURSE_PATH = "/course/get/all";

  private static final String SCORE_PATH = "/user/get/";

  private final RequestSpecification spec;

  public UserApi() {
    spec = given()
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  @Step("Получение списка пользователей")
  public ValidatableResponse getUser() {
    return given(spec)
        .basePath(GET_USER_PATH)
        .when()
        .get()
        .then()
        .log().all();
  }

  @Step("Получение списка курсов")
  public ValidatableResponse getCourse() {
    return given(spec)
        .basePath(COURSE_PATH)
        .when()
        .get()
        .then()
        .log().all();
  }

  @Step("Получение оценки пользователя")
  public ValidatableResponse getScore() {
    return given(spec)
        .basePath(SCORE_PATH)
        .when()
        .get("/78")
        .then()
        .log().all();
  }
}
