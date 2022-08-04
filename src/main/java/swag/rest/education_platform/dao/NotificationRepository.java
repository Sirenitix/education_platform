package swag.rest.education_platform.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.ClientNotification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<ClientNotification, Long> {
    List<ClientNotification> findAllByUserId(Long id, Pageable pageable);

}
