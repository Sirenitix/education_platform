package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import swag.rest.education_platform.service.MeetingService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {
    @Value("videosdk.baseUrl")
    String baseUrl;
    private final WebClient webClient;
    private final MeetingService meetingService;

    @PostMapping("/generateMeetingToken")
    public ResponseEntity<String> generateMeeting() {
        String meetingToken = meetingService.generateMeeting();
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingToken);
    }

}
