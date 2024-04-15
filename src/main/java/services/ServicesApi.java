package services;

import static common.Constants.*;
import static io.restassured.RestAssured.given;

import dto.pet.Category;
import dto.pet.PetDto;
import dto.pet.Tag;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class ServicesApi {

  private static final String BASE_URL = System.getProperty("base.api.url");
  private static final String BASE_PATH = "/pet";
  private final RequestSpecification spec;
  public PetDto petDTO;

  public ServicesApi() {
    spec = given()
        .filter(new AllureRestAssured())
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  @Step("Добавление питомца")
  public ValidatableResponse addPet(PetDto petDTO) {
    return given(spec)
        .basePath(BASE_PATH)
        .body(petDTO)
        .when()
        .post()
        .then()
        .log().all();
  }

  @Step("Создание питомца")
  public PetDto createPet() {
    List<Tag> listTag = new ArrayList<>();
    listTag.add(new Tag(29L, "cat"));

    return petDTO = PetDto.builder()
        .name(NAME_PET)
        .category(Category.builder()
            .id(47L)
            .name(CATEGORY_NAME).build())
        .tags(listTag)
        .status(STATUS_SOLD)
        .build();
  }

  @Step("Поиск питомца")
  public ValidatableResponse findPetById(long id) {
    return given(spec)
        .basePath(BASE_PATH)
        .pathParam("id", id)
        .when()
        .get("/{id}")
        .then()
        .log().all();
  }

  @Step("Изменение потомца")
  public ValidatableResponse updatePet(PetDto petDTO) {
    return given(spec)
        .basePath(BASE_PATH)
        .body(petDTO)
        .when()
        .put()
        .then()
        .log().all();
  }

  @Step("Удаление питомца")
  public ValidatableResponse deletePet(long id) {
    return given(spec)
        .basePath(BASE_PATH)
        .pathParam("id", id)
        .when()
        .delete("/{id}")
        .then()
        .log().all();
  }
}
