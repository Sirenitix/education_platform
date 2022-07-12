package swag.rest.education_platform.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private Long ownerId;
    private String content;
}
