package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class ZoomDto {
    private String password;
    private String host_email;
    private Integer type;
    private String topic;
    private String agenda;
    private String start_time;
    private String timezone;
}
