package dto.stub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {

  private String name;
  private String course;
  private String email;
  private int age;

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", course='" + course + '\'' +
        ", email='" + email + '\'' +
        ", age=" + age +
        '}';
  }
}
