package swag.rest.education_platform.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZoomSettings {
    private String alternative_hosts;
    private boolean join_before_host;
    private Integer jbh_time;
    private boolean host_video;

    public ZoomSettings() {
        this.join_before_host = true;
        this.jbh_time = 10;
        this.host_video = true;
    }
}
