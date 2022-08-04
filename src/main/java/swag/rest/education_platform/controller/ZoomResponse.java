package swag.rest.education_platform.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZoomResponse {
        @JsonProperty("id")
        long meetingId;
        @JsonProperty("created_at")
        String createdAt;
        @JsonProperty("duration")
        int duration;
        @JsonProperty("host_id")
        String hostId;
        @JsonProperty("host_email")
        String hostEmail;
        @JsonProperty("join_url")
        String joinUrl;
        @JsonProperty("start_time")
        String startTime;
        @JsonProperty("start_url")
        String startUrl;
        @JsonProperty("status")
        String status;
        @JsonProperty("timezone")
        String timezone;
        @JsonProperty("topic")
        String topic;
        @JsonProperty("type")
        int type;
        @JsonProperty("uuid")
        String uuid;
        @JsonProperty("agenda")
        private String agenda;
        @JsonProperty("password")
        private String password;
        @JsonProperty("personal_meeting_url")
        private String personalMeetingUrl;
}
