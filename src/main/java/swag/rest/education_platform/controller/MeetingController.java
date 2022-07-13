package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.service.MeetingService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/generateMeetingToken")
    public ResponseEntity<String> generateMeeting() {
        String meetingLink = meetingService.generateMeeting();
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingLink);
    }

}
