package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectStudent;

import java.util.List;

public interface ProjectMessageRepository extends JpaRepository<ProjectMessage, Long> {

    @Query("select new ProjectMessage(p.id, p.date, p.text, p.userfirstname, p.userlastname) from ProjectMessage p where p.project= :project")
    List<ProjectMessage> findByProject(ProjectStudent project);

    ProjectMessage findByTitle(String title);
}
