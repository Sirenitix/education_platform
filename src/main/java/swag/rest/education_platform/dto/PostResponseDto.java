package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.entity.ReflectionPostComment;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class PostResponseDto implements Comparable<PostResponseDto> {
    private Long id;
    private String title;
    private String username;
    private String content;
    private List<Tag> tag;
    private List<ReflectionPostComment> comments;
    private Long likes;
    String fileLink;
    String imageLink;

    @Override
    public int compareTo(PostResponseDto postResponseDto) {
        return postResponseDto.getId().compareTo(this.getId());
    }
}
