package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ClientNotification;
import swag.rest.education_platform.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

    @PostMapping()
    public List<?> getNotifications(@RequestParam Long user_id, @RequestParam Integer page) {
        List<ClientNotification> notifications = service.getNotifications(user_id, page);
        return notifications;
    }
}