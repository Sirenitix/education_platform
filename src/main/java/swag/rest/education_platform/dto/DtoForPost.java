package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoForPost {
    private Long id;
    private Long content;
    private Long ownerId;
    private String ownerName;
    private String ownerLastname;
}
