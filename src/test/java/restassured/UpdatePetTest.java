package restassured;

import static common.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import dto.pet.Category;
import dto.pet.PetDto;
import dto.pet.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.AllureRestAPI;
import services.ServicesApi;

import java.util.Random;

public class UpdatePetTest extends AllureRestAPI {

  private final ServicesApi userApi = new ServicesApi();
  private PetDto petDTO;

  //Изменение данных у существующего питомца
  //Создать питомца. Изменить любые данные и проверить, что у этого питомца (с тем же id) данные изменились
  @Test
  @DisplayName("Изменение данных у существующего питомца")
  public void updatePet() {
    ValidatableResponse response = userApi.addPet(userApi.createPet());
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    long newId = actualPet.getId();
    String name = actualPet.getName();
    String categoryName = actualPet.getCategory().getName();
    String status = actualPet.getStatus();

    petDTO = PetDto.builder()
        .id(newId)
        .name("Vasya")
        .category(Category.builder()
            .id(150L)
            .name("dog").build())
        .status(STATUS_PENDING)
        .build();

    ValidatableResponse responseUpdate = userApi.updatePet(petDTO);
    PetResponse actualUpdatePet = responseUpdate.extract().body().as(PetResponse.class);

    responseUpdate.statusCode(HttpStatus.SC_OK);

    assertAll("Check find pet by id",
        () -> Assertions.assertEquals(newId, actualUpdatePet.getId(), "The pet's id has changed"),
        () -> Assertions.assertNotEquals(name, actualUpdatePet.getName(), "The pet's name has not changed"),
        () -> Assertions.assertNotEquals(categoryName, actualUpdatePet.getCategory().getName(), "The name of the pet category has not changed"),
        () -> Assertions.assertNotEquals(status, actualUpdatePet.getStatus(), "The status of the pet has not changed")
    );

    userApi
        .deletePet(actualUpdatePet.getId())
        .statusCode(HttpStatus.SC_OK);
  }

  //Изменить данные у несуществующего питомца
  //Проверить, что питомца не существует и получить этот id
  //Проверяем, что питомец будет создан,т.к его не существует
  @Test
  @DisplayName("Изменение данных у несуществующего питомца")
  public void putInvalidPet() {
    long newId = getNonExistentPetId();
    petDTO = PetDto.builder()
        .id(newId)
        .name("Jack")
        .category(Category.builder()
            .name("dog").build())
        .status(STATUS_SOLD)
        .build();

    ValidatableResponse responseUpdate = userApi.updatePet(petDTO);
    PetResponse actualUpdatePet = responseUpdate.extract().body().as(PetResponse.class);

    responseUpdate.statusCode(HttpStatus.SC_OK);

    assertAll("Check find pet by id",
        () -> Assertions.assertEquals(newId, actualUpdatePet.getId(), "The pet's id has changed"),
        () -> Assertions.assertEquals("Jack", actualUpdatePet.getName(), "The pet's name has not changed"),
        () -> Assertions.assertEquals("dog", actualUpdatePet.getCategory().getName(), "The name of the pet category has not changed"),
        () -> Assertions.assertEquals(STATUS_SOLD, actualUpdatePet.getStatus(), "The status of the pet has not changed")
    );

    userApi
        .deletePet(actualUpdatePet.getId())
        .statusCode(HttpStatus.SC_OK);
  }

  private long getNonExistentPetId() {
    Random newInt = new Random();
    long intPet;

    while (true) {
      intPet = newInt.nextLong();
      if (intPet > 0) {
        ValidatableResponse checkPet = userApi
            .findPetById(intPet);
        int code = checkPet.extract().statusCode();
        if (code == 404) {
          break;
        }
      }
    }
    return intPet;
  }
}
