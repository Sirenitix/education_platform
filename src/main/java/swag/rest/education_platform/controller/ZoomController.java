package swag.rest.education_platform.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import swag.rest.education_platform.dto.ZoomDto;

import java.util.UUID;

@RestController
@RequestMapping("/zoom")
public class ZoomController {
    private static final String api = "https://api.zoom.us/v2/users/" +"lJgQjvcCRCOPK65monqlSw"+"/meetings";
    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IlZuRjRHQk5yVFBpWU1RMzNHaVBYMWciLCJleHAiOjE2OTcxMzM2MDAsImlhdCI6MTY1OTUyNDU2OX0.h30gVtc5JbhzePXgwKWx8Ank9wcO2tFzDOgtqK8JJJg";
    @PostMapping("/create-meeting")
    public void createMeeting() {
        ZoomDto zoom = new ZoomDto();
        zoom.setPassword("protect");
        zoom.setHost_email("rakhim.lugma@gmail.com");
        zoom.setType(2);
        zoom.setAgenda("here we gooooo");
        zoom.setTopic("Test 2");
        zoom.setTimezone("Asia/Almaty");
        zoom.setStart_time("2022-08-08T00:00:00");
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("content-type", "application/json");
        HttpEntity<ZoomDto> http = new HttpEntity<>(zoom, headers);
        System.out.println(http);
        ResponseEntity<?> response = template.exchange(api, HttpMethod.POST, http, Object.class);
        System.out.println(response.getStatusCode());
    }
}
