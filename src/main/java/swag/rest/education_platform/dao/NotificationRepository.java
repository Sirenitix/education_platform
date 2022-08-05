package swag.rest.education_platform.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ClientNotification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<ClientNotification, Long> {
    List<ClientNotification> findAllByUserId(Long id, Pageable page);

    @Query ("select n from ClientNotification n where n.id = :id and n.unRead = true ")
    Boolean checkForNewNotifications(Long id);
}
