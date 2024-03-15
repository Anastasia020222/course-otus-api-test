package dto.stub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseScore {

  private String name;
  private String score;

  @Override
  public String toString() {
    return "ResponseScore{" +
        "name='" + name + '\'' +
        ", score='" + score + '\'' +
        '}';
  }
}
