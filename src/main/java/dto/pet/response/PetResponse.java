package dto.pet.response;

import lombok.Data;

import java.util.List;

@Data
public class PetResponse {

  private CategoryResponse category;
  private String name;
  private Long id;
  private String status;
  private List<String> photoUrls;
  private List<TagResponse> tags;
}
