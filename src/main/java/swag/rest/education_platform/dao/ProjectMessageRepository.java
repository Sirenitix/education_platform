package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.ProjectMessage;

public interface ProjectMessageRepository extends JpaRepository<ProjectMessage, Long> {
}
