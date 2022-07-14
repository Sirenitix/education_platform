package swag.rest.education_platform.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
}
