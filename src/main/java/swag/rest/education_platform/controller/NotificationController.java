package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ClientNotification;
import swag.rest.education_platform.service.NotificationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

//    @GetMapping("/all")
//    public List<?> getNotifications(@RequestParam Long user_id, @RequestParam Integer page) {
//        List<ClientNotification> notifications = service.getNewNotifications(user_id, page);
//        return notifications;
//    }

    @GetMapping
    public List<?> getAllNotifications(Principal principal, @RequestParam Integer page) {
        List<ClientNotification> notifications = service.getAllNotifications(principal.getName(), page);
        return notifications;
    }

    @GetMapping("/check")
    public boolean checkForNotification(Principal principal) {
        return service.checkForNotifications(principal.getName());
    }

}
