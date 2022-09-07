package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProjectPostDto {

    Long project_id;
    String text;
    String title;
    MultipartFile file;
    MultipartFile image;

}
