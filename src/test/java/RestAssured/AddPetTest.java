package RestAssured;

import static common.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import dto.pet.Category;
import dto.pet.PetDto;
import dto.pet.Tag;
import dto.pet.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServicesApi;

import java.util.ArrayList;
import java.util.List;

public class AddPetTest {

  private final ServicesApi userApi = new ServicesApi();
  private PetDto petDTO;

  //Проверяем создание нового питомца с заданным id
  @Test
  public void addPet() {
    List<Tag> listTag = new ArrayList<>();
    listTag.add(new Tag(29L, "cat"));

    petDTO = PetDto.builder()
        .id(ID)
        .name(NAME_PET)
        .category(Category.builder()
            .id(47L)
            .name(CATEGORY_NAME).build())
        .tags(listTag)
        .status(STATUS_SOLD)
        .build();

    ValidatableResponse response = userApi.addPet(petDTO);
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    response.statusCode(HttpStatus.SC_OK);

    assertAll("Check add pet response",
        () -> Assertions.assertEquals(ID, actualPet.getId(), "IncorrectId"),
        () -> Assertions.assertEquals(NAME_PET, actualPet.getName(), "IncorrectName"),
        () -> Assertions.assertEquals(47L, actualPet.getCategory().getId(), "IncorrectCategoryName"),
        () -> Assertions.assertEquals(CATEGORY_NAME, actualPet.getCategory().getName(), "IncorrectCategoryId"),
        () -> Assertions.assertEquals(STATUS_SOLD, actualPet.getStatus(), "IncorrectStatus")
    );

    userApi
        .deletePet(actualPet.getId())
        .statusCode(HttpStatus.SC_OK);
  }
}
