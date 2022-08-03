package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.service.ProjectMessageService;
import swag.rest.education_platform.service.ProjectStudentService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectStudentService projectStudentService;
    private final ProjectMessageService projectMessageService;

    @GetMapping("/projects")
    public List<?> getProjects(Principal principal) {
        List<ProjectStudent> projects = projectStudentService.getProjectByUsername(principal.getName());
        return projects;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestParam(name = "title") String projectTitle,
                                           @RequestParam(name = "description") String projectDescription,
                                           @RequestBody UsersDto usersDto) {
        projectStudentService.createProject(projectTitle, projectDescription, usersDto.getUsers());
        return ResponseEntity.status(HttpStatus.OK).body("Project has been created");

    }


    @PutMapping("/projects")
    public ResponseEntity<?> editProject(Principal principal, @RequestBody ProjectStudent projectStudent) {
        projectStudentService.updateProject(projectStudent, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been changed");

    }

    @PostMapping("project/addPost")
    public ResponseEntity<?> createPost(@RequestParam Long project_id, @RequestParam String content,@RequestParam String title, Principal principal ) {
        projectMessageService.addMessage(project_id,content, principal.getName(), title);
        return ResponseEntity.status(HttpStatus.OK).body("Message has been sent");
    }

    @GetMapping("project/{id}/posts")
    public List<?> getPost(@PathVariable Long id) {

        return projectMessageService.getMessages(id);
    }

    @GetMapping("project/search")
    public Set<ProjectStudent> search(@RequestParam String title, @RequestParam String description) {

        return  projectStudentService.search(title, description);
    }
}
