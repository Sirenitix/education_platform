package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.ProjectStudent;

public interface ProjectStudentRepository extends JpaRepository<ProjectStudent, Long> {
}
