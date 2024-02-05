package dto.response;

import lombok.Data;

@Data
public class PetErrorResponse {

  private int code;
  private String type;
  private String message;
}
