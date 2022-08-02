package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;

import java.util.List;

public interface ProjectStudentRepository extends JpaRepository<ProjectStudent, Long> {

    List<ProjectStudent> findProjectStudentByUsers(Users user);

//    @Query("Select new ProjectStudent (p.id, p.title, p.description) from ProjectStudent p where p..id = :id ")
//    List<ProjectStudent> findprojectsbyuser(Long id);
}
