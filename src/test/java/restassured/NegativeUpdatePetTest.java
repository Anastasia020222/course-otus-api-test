package restassured;

import static common.Constants.NAME_PET;
import static common.Constants.STATUS_SOLD;
import static org.junit.jupiter.api.Assertions.assertAll;

import dto.pet.PetDto;
import dto.pet.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.AllureRestAPI;
import services.ServicesApi;

public class NegativeUpdatePetTest extends AllureRestAPI {

  private final ServicesApi userApi = new ServicesApi();
  private PetDto petDTO;

  //Проверяем создание питомца без указания id
  @Test
  @DisplayName("Создание потомца по id")
  public void addPetWithoutId() {
    petDTO = PetDto.builder()
        .name(NAME_PET)
        .status(STATUS_SOLD)
        .build();

    ValidatableResponse response = userApi.addPet(petDTO);
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    response.statusCode(HttpStatus.SC_OK);

    assertAll("Check add pet response",
        () -> Assertions.assertNotEquals(0, actualPet.getId(), "IncorrectId"),
        () -> Assertions.assertEquals(NAME_PET, actualPet.getName(), "IncorrectName"),
        () -> Assertions.assertEquals(STATUS_SOLD, actualPet.getStatus(), "IncorrectStatus")
    );

    userApi
        .deletePet(actualPet.getId())
        .statusCode(HttpStatus.SC_OK);
  }
}
