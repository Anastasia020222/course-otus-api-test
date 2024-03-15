package dto.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PetDto {

  private Category category;
  private Long id;
  private String name;
  private List<String> photoUrls;
  private String status;
  private List<Tag> tags;

}
