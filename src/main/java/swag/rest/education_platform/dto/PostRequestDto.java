package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    MultipartFile file;
    MultipartFile image;

}
