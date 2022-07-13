package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.ProjectMessageRepository;
import swag.rest.education_platform.dao.ProjectStudentRepository;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.ProjectStudentNotFound;
import swag.rest.education_platform.exception.UserNotInProjectException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectStudentService {
    private final ProjectStudentRepository repository;
    private final UserService userService;
    private final ProjectStudentRepository projectStudentRepository;
    private final ProjectMessageRepository projectMessageRepository;
    private final UserRepository userRepository;
    public ProjectStudent getProjectById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Transactional(readOnly = true)
    public List<ProjectStudent> getProjectByUsername(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        if (user.getProjects() == null)
//            return null;
//        for(ProjectStudent s : user.getProjects()) {
//            System.out.println(s.getTitle());
//        }

        List<ProjectStudent> projects = projectStudentRepository.findProjectStudentByUsers(user);
        for(ProjectStudent p : projects) {
           for(Users u:  p.getUsers())
           {
               u.setProjects(null);
               u.setPassword(null);
               u.setSciencePosts(null);
               u.setReflectionPosts(null);
               u.setReflectionPostComments(null);
               u.setSciencePostComments(null);

           }
            p.setMessages(null);
            p.setMessages(projectMessageRepository.findByProject(p));

        }

        return projectStudentRepository.findProjectStudentByUsers(user);//user.getProjects();
    }

    @Transactional
    public void createProject(String username, String project_name, String[] users) {
        List<Users> usersList = new ArrayList<>();
        for (String s : users) {
            usersList.add(userService.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("user not found")));
        }
        ProjectStudent project = new ProjectStudent();
        project.setUsers(usersList);
        project.setTitle(project_name);
        repository.save(project);
        System.out.println(project.getUsers().toString());
    }

    public void updateProject(ProjectStudent projectStudent, Principal principal) {
        String username = principal.getName();
        Users currentUser = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        ProjectStudent projectStudentFromDb = repository.findById(projectStudent.getId()).orElseThrow(() -> (new ProjectStudentNotFound("Project not found!")));
        List<Users> hasProject = projectStudentFromDb.getUsers().stream().filter(user -> Objects.equals(user.getId(), currentUser.getId())).collect(Collectors.toList());
        if (hasProject.isEmpty()) {
            throw new UserNotInProjectException("This user is not participated in this project");
        }
    }
}
