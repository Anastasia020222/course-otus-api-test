package stub;

import dto.stub.Courses;
import dto.stub.ResponseScore;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import dto.stub.ResponseUser;
import services.UserApi;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class RestApiStubTest {

  private final UserApi userApi = new UserApi();


  // /user/get/all - для получения списка всех пользователей
  // для запуска тестов нужно запустить wiremock на виртуалке с конфигами
  @Test
  public void getUser() {
    ValidatableResponse response = userApi.getUser();
    ResponseUser responseUser = response.extract().body().as(ResponseUser.class);
    response.statusCode(HttpStatus.SC_OK);

    for (ResponseUser user : List.of(responseUser)) {
      System.out.println("Список пользователей: " + "\n" + user);
    }
    assertTrue(!List.of(responseUser).isEmpty());
  }

  // /cource/get/all для получения списка курсов
  @Test
  public void getCourse() {
    ValidatableResponse response = userApi.getCourse();
    Courses[] getCourseList = response.extract().body().as(Courses[].class);
    response.statusCode(HttpStatus.SC_OK);

    System.out.println("Список курсов: ");
    for (var course : getCourseList) {
      System.out.println(course);
    }
    assertTrue(getCourseList.length > 0);
  }

  // /user/get/{id} для получение оценки пользователя
  @Test
  public void getScore() {
    ValidatableResponse response = userApi.getScore();
    ResponseScore responseScore = response.extract().body().as(ResponseScore.class);

    for (ResponseScore score : List.of(responseScore)) {
      System.out.println("Оценка пользователя: " + score.getScore());
    }
    assertTrue(!List.of(responseScore).isEmpty());
  }
}
