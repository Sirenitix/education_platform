package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.ProjectMessageRepository;
import swag.rest.education_platform.dao.ProjectStudentRepository;
import swag.rest.education_platform.dao.TagRepository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.ProjectStudentNotFound;
import swag.rest.education_platform.exception.UserNotInProjectException;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectStudentService {
    private final ProjectStudentRepository repository;
    private final UserService userService;
    private final ProjectStudentRepository projectStudentRepository;
    private final ProjectMessageRepository projectMessageRepository;
    private final TagRepository tagRepository;

    public ProjectStudent getProjectById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Transactional(readOnly = true)
    public List<ProjectStudent> getProjectByUsername(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<ProjectStudent> projects = projectStudentRepository.findProjectStudentByUsers(user);
        for (ProjectStudent p : projects) {
            p.setMessages(projectMessageRepository.findByProject(p));
        }
        return projectStudentRepository.findProjectStudentByUsers(user);//user.getProjects();
    }

    public Set<ProjectStudent> search(String title, String description) {


        Set<ProjectStudent> result = new HashSet<>();// = repository.findAllByTitleContaining(title);
        if(title.isEmpty() && description.isEmpty()) return result;

        List<ProjectStudent> findAll = repository.findAll();
        if(title != null)  {
            result = findAll.stream().filter(u -> u.getTitle().contains(title)).collect(Collectors.toSet());
        }
        if(description != null) {
            result = findAll.stream().filter(u -> u.getDescription().contains(description)).collect(Collectors.toSet());
        }
        return result;

    }

//    @Transactional(readOnly = true)
//    public List<ProjectStudent> getProjectDetails(String username) {
//        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return projectStudentRepository.findProjectStudentByUsers(user.getId());
//    }

    @Transactional
    public void createProject(String title, String description, List<String> users) {
        List<Users> usersList = new ArrayList<>();
        for (String s : users) {
            usersList.add(userService.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("user not found")));
        }
        ProjectStudent project = new ProjectStudent();
        project.setUsers(usersList);
        project.setTitle(title);
        project.setDescription(description);
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
