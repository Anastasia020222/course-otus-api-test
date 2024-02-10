import static org.junit.jupiter.api.Assertions.assertAll;

import dto.response.PetErrorResponse;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ServicesApi;

public class NegativeFindPetTest {

  private final ServicesApi userApi = new ServicesApi();

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
