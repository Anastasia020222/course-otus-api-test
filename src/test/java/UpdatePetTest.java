import dto.Category;
import dto.PetDto;
import dto.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServicesApi;

import java.util.Random;

import static dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UpdatePetTest {

  public ServicesApi userApi = new ServicesApi();
  public PetDto petDTO;

  //Изменение данных у существующего питомца
  //Создать питомца. Изменить любые данные и проверить, что у этого питомца (с тем же id) данные изменились
  @Test
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
        .status(statusPending)
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
  }

  //Изменить данные у несуществующего питомца
  //Проверить, что питомца не существует и получить этот id
  //Проверяем, что питомец будет создан,т.к его не существует
  @Test
  public void putInvalidPet() {
    long newId = getPetId();
    petDTO = PetDto.builder()
        .id(newId)
        .name("Jack")
        .category(Category.builder()
            .name("dog").build())
        .status(statusSold)
        .build();

    ValidatableResponse responseUpdate = userApi.updatePet(petDTO);
    PetResponse actualUpdatePet = responseUpdate.extract().body().as(PetResponse.class);

    responseUpdate.statusCode(HttpStatus.SC_OK);

    assertAll("Check find pet by id",
        () -> Assertions.assertEquals(newId, actualUpdatePet.getId(), "The pet's id has changed"),
        () -> Assertions.assertEquals("Jack", actualUpdatePet.getName(), "The pet's name has not changed"),
        () -> Assertions.assertEquals("dog", actualUpdatePet.getCategory().getName(), "The name of the pet category has not changed"),
        () -> Assertions.assertEquals(statusSold, actualUpdatePet.getStatus(), "The status of the pet has not changed")
    );
  }

  private long getPetId() {
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
