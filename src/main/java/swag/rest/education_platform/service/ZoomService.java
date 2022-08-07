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
import swag.rest.education_platform.controller.ZoomSettings;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.dto.ZoomDto;
import swag.rest.education_platform.dto.ZoomRequestDto;
import swag.rest.education_platform.email_client.service.EmailSenderService;
import swag.rest.education_platform.entity.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ZoomService {
    private static final String api = "https://api.zoom.us/v2/users/" + "lJgQjvcCRCOPK65monqlSw" + "/meetings";
    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IlZuRjRHQk5yVFBpWU1RMzNHaVBYMWciLCJleHAiOjE2OTcxMzM2MDAsImlhdCI6MTY1OTUyNDU2OX0.h30gVtc5JbhzePXgwKWx8Ank9wcO2tFzDOgtqK8JJJg";
    //todo hide the token
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final NotificationService notificationService;


    public String createMeeting(ZoomRequestDto requestDto, String currentUser) {
        Set<String> users = new HashSet<>(requestDto.getUsers());
        users.add(currentUser);
        ZoomDto zoom = new ZoomDto();
//        if(!dateMatch(time)) throw new RuntimeException("Incorrect Time format");
        zoom.setPassword("protect");
        zoom.setType(2);
        zoom.setTimezone("Asia/Almaty");
        zoom.setStart_time(requestDto.getStart_time());
        zoom.setAgenda(requestDto.getAgenda());
        zoom.setTopic(requestDto.getTopic());
//        zoom.setStart_time("2022-08-08T00:00:00");
        ZoomSettings settings = new ZoomSettings();
        zoom.setSettings(settings);
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
            throw new RuntimeException("Meeting has not been created");
        }

        for (String u : users) {
//            emailSenderService.sendEmailWithAttachment(u, result.getJoinUrl());
            Users user = userService.findByUsername(u).orElse(null);
            if(user == null) continue;
            notificationService.addNotification(user.getId(), "You have been invited to the following meeting: " +
                     result.getJoinUrl() + "\n at : " + result.getStartTime());
        }

        return result.getJoinUrl();
    }

    public boolean dateMatch(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
        //todo make util class and make patter static
        return pattern.matcher(date).matches();
    }


}
