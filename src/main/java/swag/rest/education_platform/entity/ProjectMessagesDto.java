package swag.rest.education_platform.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProjectMessagesDto {
    private Long user_id;
    private String username;
    private String content;
    private LocalDate date;
}
