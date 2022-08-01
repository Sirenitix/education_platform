package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectMessagesDto;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.service.ProjectMessageService;
import swag.rest.education_platform.service.ProjectStudentService;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<?> createProject(@RequestParam(name = "Project Title") String projectTitle,
                                           @RequestParam(name = "Project Description") String projectDescription,
                                           String[] users) {
        projectStudentService.createProject(projectTitle, projectDescription, users);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been created");

    }


    @PutMapping("/projects")
    public ResponseEntity<?> editProject(Principal principal, @RequestBody ProjectStudent projectStudent) {
        projectStudentService.updateProject(projectStudent, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been changed");

    }

    @PostMapping("project/{id}/addMessage")
    public ResponseEntity<?> createMessage(@PathVariable Long id,  Principal principal, @RequestParam String message) {
        projectMessageService.addMessage(id,message, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Message has been sent");
    }

    @GetMapping("project/{id}/messages")
    public List<?> getMessage(@PathVariable Long id) {

        return projectMessageService.getMessages(id);
    }
}
