package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {
}
