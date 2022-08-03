package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;

import java.util.List;
import java.util.Set;

public interface ProjectStudentRepository extends JpaRepository<ProjectStudent, Long> {

    List<ProjectStudent> findProjectStudentByUsers(Users user);

    Set<ProjectStudent> findAllByTitleContaining(String title);
    Set<ProjectStudent> findAllByDescriptionContaining(String description);
//    @Query("Select new ProjectStudent (p.id, p.title, p.description) from ProjectStudent p where p..id = :id ")
//    List<ProjectStudent> findprojectsbyuser(Long id);
}
