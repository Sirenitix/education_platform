package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.service.ZoomService;

@RestController
@RequestMapping("/zoom")
@RequiredArgsConstructor
public class ZoomController {

    private final ZoomService service;

    @PostMapping("/create-meeting")
    public ResponseEntity<String> createMeeting(@RequestParam String time, @RequestParam UsersDto users) {
        String meeting = service.createMeeting(time, users);
        return ResponseEntity.status(HttpStatus.CREATED).body("Meeting has been created by URL : " + meeting);
    }
}
