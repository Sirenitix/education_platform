package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.entity.ReflectionPostComment;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private List<Tag> tag;
    private List<ReflectionPostComment> comments;
    private Long likes;
}
