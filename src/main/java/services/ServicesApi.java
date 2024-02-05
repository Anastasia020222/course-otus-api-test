package services;

import dto.Category;
import dto.PetDto;
import dto.Tag;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static dto.Constants.*;
import static io.restassured.RestAssured.given;

public class ServicesApi {

  private static final String BASE_URL = "https://petstore.swagger.io/v2";
  private static final String BASE_PATH = "/pet";
  private RequestSpecification spec;
  public PetDto petDTO;

  public ServicesApi() {
    spec = given()
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON)
        .log().all();
  }

  public ValidatableResponse addPet(PetDto petDTO) {
    return given(spec)
        .basePath(BASE_PATH)
        .body(petDTO)
        .when()
        .post()
        .then()
        .log().all();
  }

  public PetDto createPet() {
    List<Tag> listTag = new ArrayList<>();
    listTag.add(new Tag(29L, "cat"));

    return petDTO = PetDto.builder()
        .name(namePet)
        .category(Category.builder()
            .id(47L)
            .name(categoryName).build())
        .tags(listTag)
        .status(statusSold)
        .build();
  }

  public ValidatableResponse findPetById(long id) {
    return given(spec)
        .basePath(BASE_PATH)
        .pathParam("id", id)
        .when()
        .get("/{id}")
        .then()
        .log().all();
  }

  public ValidatableResponse updatePet(PetDto petDTO) {
    return given(spec)
        .basePath(BASE_PATH)
        .body(petDTO)
        .when()
        .put()
        .then()
        .log().all();
  }
}
