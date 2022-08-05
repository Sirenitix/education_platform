package swag.rest.education_platform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import swag.rest.education_platform.controller.ZoomResponse;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.dto.ZoomDto;
import swag.rest.education_platform.email_client.service.EmailSenderService;
import swag.rest.education_platform.entity.Users;

@Service
@RequiredArgsConstructor
public class ZoomService {
    private static final String api = "https://api.zoom.us/v2/users/" +"lJgQjvcCRCOPK65monqlSw"+"/meetings";
    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IlZuRjRHQk5yVFBpWU1RMzNHaVBYMWciLCJleHAiOjE2OTcxMzM2MDAsImlhdCI6MTY1OTUyNDU2OX0.h30gVtc5JbhzePXgwKWx8Ank9wcO2tFzDOgtqK8JJJg";
    //todo hide the token
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final NotificationService notificationService;


    public String createMeeting(String time, UsersDto users) {
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
        ResponseEntity<String> response = template.exchange(api, HttpMethod.POST, http, String.class);
        ObjectMapper mapper = new ObjectMapper();
        ZoomResponse result;
        try {
            result = mapper.readValue(response.getBody(), ZoomResponse.class);
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for(String u : users.getUsers()) {
//            emailSenderService.sendEmailWithAttachment(u, result.getJoinUrl());
            Users user = userService.findByUsername(u).orElseThrow(() -> new UsernameNotFoundException("User not found"));
          notificationService.addNotification(user.getId(), " Dear " +  user.getFirstname() + ", \nYou have been invited to the following meeting: " +
                  "Zoom meeting url : " + result.getJoinUrl());
        }

        return result.getJoinUrl();
    }


}
