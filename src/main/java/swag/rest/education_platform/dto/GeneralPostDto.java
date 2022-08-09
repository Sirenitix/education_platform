package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GeneralPostDto {

    private String title;
    private String content;
    private Long likes;
    MultipartFile file;
    MultipartFile image;

}
