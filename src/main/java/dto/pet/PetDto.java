package dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PetDto {

  private Category category;
  private Long id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;

}
