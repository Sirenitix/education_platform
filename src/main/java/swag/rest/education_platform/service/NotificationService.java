package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.NotificationRepository;
import swag.rest.education_platform.entity.ClientNotification;
import swag.rest.education_platform.entity.Users;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
   //todo

    public List<ClientNotification> getAllNotifications(Long id, Integer page) {
        Pageable paging = PageRequest.of(page, 10);
        List<ClientNotification> notifications = repository.findAllByUserId(id,paging);
        return notifications;
    }
    public List<ClientNotification> getNewNotifications(Long id, Integer page) {
        Pageable paging = PageRequest.of(page, 10);
        List<ClientNotification> notifications = repository.findAllByNewTrue(id,paging);
        return notifications;
    }
    public void addNotification(Long id, String text) {
        ClientNotification notification = new ClientNotification();
        notification.setId(id);
        notification.setText(text);
    }
}
