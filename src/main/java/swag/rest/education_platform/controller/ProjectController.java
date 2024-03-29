package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.ProjectPostDto;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.dto.UsersDto;
import swag.rest.education_platform.service.ProjectMessageService;
import swag.rest.education_platform.service.ProjectStudentService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
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
                                           @RequestBody UsersDto usersDto,
                                           Principal principal) {
        projectStudentService.createProject(projectTitle, projectDescription, usersDto.getUsers(), principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Project has been created");

    }


    @PutMapping("/projects")
    public ResponseEntity<?> editProject(Principal principal, @RequestBody ProjectStudent projectStudent) {
        projectStudentService.updateProject(projectStudent, principal);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been changed");

    }

    @PostMapping("project/addPost")
    public ResponseEntity<?> createPost(@ModelAttribute ProjectPostDto projectPostDto, Principal principal ) throws IOException {
        projectMessageService.addMessage(projectPostDto, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Message has been sent");
    }

    @GetMapping("project/{id}/posts")
    public List<?> getPost(@PathVariable Long id) {
        return projectMessageService.getMessages(id);
    }

    @GetMapping("project/search")
    public Set<ProjectStudent> search(@RequestParam(required = false, defaultValue = "")String title, @RequestParam(required = false,defaultValue = "") String description) {
        return  projectStudentService.search(title, description);
    }

    @Transactional
    @GetMapping("/project/projectPostFile/{message_id}")
    public ResponseEntity<?> getPostFileById(@PathVariable Long message_id) throws IOException {
        ProjectMessage projectMessage = projectMessageService.getMessage(message_id);
        log.info(Arrays.toString(projectMessage.getFile()) + " - file");
        HttpHeaders responseHeaders = new HttpHeaders();
        String contentType = URLConnection.
                guessContentTypeFromStream(new ByteArrayInputStream(projectMessage.getFile()));
        responseHeaders.setContentType(MediaType.valueOf(contentType));
        responseHeaders.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(projectMessage.getFile(), responseHeaders, HttpStatus.OK);
    }
    @Transactional
    @GetMapping("/project/projectPostImage/{message_id}")
    public ResponseEntity<?> getPostImageById(@PathVariable Long message_id) {
        ProjectMessage projectMessage = projectMessageService.getMessage(message_id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);
        responseHeaders.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(projectMessage.getImage(), responseHeaders, HttpStatus.OK);
    }

}
