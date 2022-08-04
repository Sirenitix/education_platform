package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.controller.ZoomSettings;

@Getter
@Setter
public class ZoomDto  {
    private String password;
    private String host_email;
    private Integer type;
    private String topic;
    private String agenda;
    private String start_time;
    private String timezone;
    private ZoomSettings settings;

}
