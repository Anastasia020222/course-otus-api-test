package dto.stub;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

  private String name;
  private int price;

  @Override
  public String toString() {
    return "Course{"
        + "name='" + name + '\''
        + ", price='" + price + '\''
        + '}';
  }
}
