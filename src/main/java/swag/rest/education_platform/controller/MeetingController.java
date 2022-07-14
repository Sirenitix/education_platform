package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.EmailRequest;
import swag.rest.education_platform.email_client.entity.Email;
import swag.rest.education_platform.email_client.service.EmailSenderService;
import swag.rest.education_platform.service.MeetingService;

import javax.mail.MessagingException;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    EmailSenderService emailSenderService;



    @PostMapping("/generateMeetingAndSend")
    public ResponseEntity<List<String>> generateMeeting(@RequestBody Email email) {
        List<String> meetingList = meetingService.generateMeeting(1);
        String meetingLink = meetingList.get(0);
        try {
             emailSenderService.sendEmailWithAttachment(email, meetingLink);
        } catch (MessagingException e) {
             throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingList);
    }



}
