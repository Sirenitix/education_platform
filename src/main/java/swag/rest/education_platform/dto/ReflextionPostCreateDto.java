package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.entity.Tag;

import java.util.Set;

@Getter
@Setter
public class ReflextionPostCreateDto {
    private String title;
    private String content;
    private Tag tag;
}
