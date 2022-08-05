package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.dto.ZoomDto;
import swag.rest.education_platform.dto.ZoomRequestDto;
import swag.rest.education_platform.service.ZoomService;

import java.security.Principal;

@RestController
@RequestMapping("/zoom")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ZoomController {

    private final ZoomService service;

    @PostMapping("/create-meeting")
    public ResponseEntity<String> createMeeting(@RequestBody ZoomRequestDto zoom, Principal principal) {
        String meeting = service.createMeeting(zoom, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Meeting has been created by URL : " + meeting);
    }
}
