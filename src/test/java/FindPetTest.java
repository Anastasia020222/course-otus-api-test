import dto.response.PetErrorResponse;
import dto.response.PetResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServicesApi;

import static org.junit.jupiter.api.Assertions.assertAll;

public class FindPetTest {

  public ServicesApi userApi = new ServicesApi();

  //Проверяем поиск питомца по id
  //Добавить питомца и получить его id
  //Сделать get запрос на получение питомца по полученному id в шаге выше
  //Проверить, что id у созданного питомца и полученого совпадают, ответ 200
  @Test
  public void findPetId() {
    ValidatableResponse response = userApi.addPet(userApi.createPet());
    PetResponse actualPet = response.extract().body().as(PetResponse.class);

    long id = actualPet.getId();

    ValidatableResponse responseFind = userApi.findPetById(id);
    PetResponse actualResponseFind = responseFind.extract().body().as(PetResponse.class);

    responseFind.statusCode(HttpStatus.SC_OK);

    assertAll("Check find pet by id",
        () -> Assertions.assertEquals(id, actualResponseFind.getId(), "IncorrectId"),
        () -> Assertions.assertEquals(actualPet.getName(), actualResponseFind.getName(), "IncorrectName"),
        () -> Assertions.assertEquals(actualPet.getCategory().getName(), actualResponseFind.getCategory().getName(), "IncorrectCategoryName")
    );
  }

  //Проверяем поиск питомца по неверному/не существующему id
  //Делаем запрос с любым неверным id
  //Проверяем, что код 404, проверяем ответ: code, type и message
  @Test
  public void findPetIncorrectId() {
    long id = 768857L;

    ValidatableResponse response = userApi.findPetById(id);
    PetErrorResponse actualPet = response.extract().body().as(PetErrorResponse.class);

    response.assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

    assertAll("Check find pet incorrect id",
        () -> Assertions.assertEquals(1, actualPet.getCode(), "Incorrect code"),
        () -> Assertions.assertEquals("error", actualPet.getType(), "Incorrect type"),
        () -> Assertions.assertEquals("Pet not found", actualPet.getMessage(), "Incorrect message")
    );
  }

  //Проверяем поиск питомца с отрицательным id
  @Test
  public void findMinusId() {
    long id = -7688579L;

    ValidatableResponse response = userApi.findPetById(id);
    PetErrorResponse actualPet = response.extract().body().as(PetErrorResponse.class);

    response.statusCode(HttpStatus.SC_NOT_FOUND);

    assertAll("Check find pet incorrect id",
        () -> Assertions.assertEquals(1, actualPet.getCode(), "Incorrect code"),
        () -> Assertions.assertEquals("error", actualPet.getType(), "Incorrect type"),
        () -> Assertions.assertEquals("Pet not found", actualPet.getMessage(), "Incorrect message")
    );
  }
}
