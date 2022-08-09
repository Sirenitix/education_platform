package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ReflextionPostCreateDto {
    private String title;
    private String content;
    private List<String> tag;
    MultipartFile file;
    MultipartFile image;
}
