package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class PostResponseWithoutTag {
    private Long id;
    private String title;
    private String username;
    private String content;
}
