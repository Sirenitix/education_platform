package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.ProjectStudentRepository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStudentService {
    private final ProjectStudentRepository repository;
    private final UserService userService;

    public ProjectStudent getProjectById(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Project not found"));
    }

    public List<ProjectStudent> getProjectByUsername(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getProjects() == null)
            return user.getProjects();
        else {
            return null;
        }
    }

    public void createProject(String username, String project_name, List<String> users) {
        List<Users> usersList = new ArrayList<>();
        for (String s : users) {
            usersList.add(userService.findByUsername(s).orElseThrow(()-> new UsernameNotFoundException("user not found")));
        }
        ProjectStudent project = new ProjectStudent();
        project.setUsers(usersList);
        project.setTitle(project_name);
        repository.save(project);
    }
}
