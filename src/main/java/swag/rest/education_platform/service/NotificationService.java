package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.NotificationRepository;
import swag.rest.education_platform.entity.ClientNotification;
import swag.rest.education_platform.entity.Users;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final UserService userService;
   //todo


    public List<ClientNotification> getAllNotifications(String username, Integer page) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));

        Pageable paging = PageRequest.of(page, 10);
        List<ClientNotification> notifications = repository.findAllByUserId(user.getId(),paging);
        for(ClientNotification notification : notifications) {
            notification.setUnRead(false);
            repository.save(notification);
        }

        return notifications;
    }
//    public List<ClientNotification> getNewNotifications(Long id, Integer page) {
//        Pageable paging = PageRequest.of(page, 10);
//        List<ClientNotification> notifications = repository.findAllByNewTrue(id,paging);
//        return notifications;
//    }
    @Transactional
    public void addNotification(Long id, String text) {
        ClientNotification notification = new ClientNotification();
        notification.setUserId(id);
        notification.setUnRead(true);
        notification.setText(text);
        repository.save(notification);
    }

    public boolean checkForNotifications(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return repository.checkForNewNotifications (user.getId()).size()>0;

    }
}
