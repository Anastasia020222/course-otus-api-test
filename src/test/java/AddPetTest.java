import dto.Category;
import dto.PetDto;
import dto.Tag;
import dto.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServicesApi;

import java.util.ArrayList;
import java.util.List;

import static dto.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AddPetTest {

  public ServicesApi userApi = new ServicesApi();
  public PetDto petDTO;

  //Проверяем создание нового питомца с заданным id
  @Test
  public void addPet() {
    long id = 557L;

    List<Tag> listTag = new ArrayList<>();
    listTag.add(new Tag(29L, "cat"));

    petDTO = PetDto.builder()
        .id(id)
        .name(namePet)
        .category(Category.builder()
            .id(47L)
            .name(categoryName).build())
        .tags(listTag)
        .status(statusSold)
        .build();

    ValidatableResponse response = userApi.addPet(petDTO);
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    response.statusCode(HttpStatus.SC_OK);

    assertAll("Check add pet response",
        () -> Assertions.assertEquals(id, actualPet.getId(), "IncorrectId"),
        () -> Assertions.assertEquals(namePet, actualPet.getName(), "IncorrectName"),
        () -> Assertions.assertEquals(47L, actualPet.getCategory().getId(), "IncorrectCategoryName"),
        () -> Assertions.assertEquals(categoryName, actualPet.getCategory().getName(), "IncorrectCategoryId"),
        () -> Assertions.assertEquals(statusSold, actualPet.getStatus(), "IncorrectStatus")
    );
  }

  //Проверяем создание питомца без указания id
  @Test
  public void addPetWithoutId() {
    petDTO = PetDto.builder()
        .name(namePet)
        .status(statusSold)
        .build();

    ValidatableResponse response = userApi.addPet(petDTO);
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    response.statusCode(HttpStatus.SC_OK);

    assertAll("Check add pet response",
        () -> Assertions.assertNotEquals(0, actualPet.getId(), "IncorrectId"),
        () -> Assertions.assertEquals(namePet, actualPet.getName(), "IncorrectName"),
        () -> Assertions.assertEquals(statusSold, actualPet.getStatus(), "IncorrectStatus")
    );
  }
}
