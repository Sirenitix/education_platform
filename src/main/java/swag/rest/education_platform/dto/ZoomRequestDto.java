package swag.rest.education_platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.controller.ZoomSettings;

import java.util.List;

@Getter
@Setter
public class ZoomRequestDto {
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Integer type;
    private String topic;
    private String agenda;
    private String start_time;
    @JsonIgnore
    private String timezone;
    @JsonIgnore
    private ZoomSettings settings;
    private List<String> users;
}
