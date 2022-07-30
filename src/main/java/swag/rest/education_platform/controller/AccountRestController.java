package swag.rest.education_platform.controller;


import com.dropbox.core.DbxException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.dto.UserFullDto;
import swag.rest.education_platform.dto.UserReponseDto;
import swag.rest.education_platform.dto.UserWithCred;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.service.AccountService;
import swag.rest.education_platform.service.DropBoxService;
import swag.rest.education_platform.service.ProjectStudentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountRestController  {
    private final DropBoxService dropbox;

    private final AccountService service;
    private final ProjectStudentService projectStudentService;
    @GetMapping("/users")
    public List<UserReponseDto> getUsers() {
        List<UserReponseDto> users = service.getUsers();
        return users;
    }


    @GetMapping("/current-user")
    public UserReponseDto currentUser(Principal principal) {
        UserReponseDto users = service.getCurrentUser(principal.getName());
        return users;
    }

    @PostMapping("/activate-user/{id}")
    public ResponseEntity<?> currentUser(@PathVariable("id") Long id) {
        service.setEnableTrue(id);
        return ResponseEntity.status(HttpStatus.OK).body("User activated");
    }

    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody UserDto user) {
        service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
    }
    @PostMapping("/full-register")
    public ResponseEntity<String> registerUserWithFullDetails(@RequestBody UserFullDto user) {
        service.registerWithFullDetails(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
    }

    @PostMapping("/user/avatar")
    public ResponseEntity<String> updateProfilePicture(@RequestParam(name = "image") MultipartFile file, Principal principal){
        UUID id = UUID.randomUUID();
        service.updateProfileImage(id,principal.getName());
        try {
            dropbox.uploadFile(file, id.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Image has been updated");
    }

    @GetMapping(value = "/user/avatar/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getProfilePicture(@PathVariable Long id) {
        Avatar image = service.getImage(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
//        responseHeaders.setContentType(MediaType.IMAGE_JPEG);
//        responseHeaders.setContentDisposition(ContentDisposition.inline().build());
        return  ResponseEntity.ok()
                .contentLength(image.getPicByte().length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getPicByte());

    }



//    @Operation(description = "Login")
//    @PostMapping("/login")
//    public void fakeLogin(@RequestBody Users user) {
//        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
//    }


    @PostMapping("/auth")
    public Map<String,String> authenticateUser(@Valid @RequestBody UserDto user, HttpServletRequest request) {
        String jwt =  service.authenticate(user,request);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwt);
        return token;
    }


//    @Operation(description = "Logout")
//    @PostMapping("/logout")
//    public void fakeLogout() {
//        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
//    }

    @Operation(description = "Rated student list")
    @GetMapping("/admin")
    public String aboutAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminCredentials adminCredentials = new AdminCredentials(
                auth.getPrincipal().toString(),
                auth.getAuthorities().toString().equals("[ROLE_ADMIN]"));
            return auth.getAuthorities().toString();
    }

    @GetMapping("/projects")
    public List<?> getProjects (Principal principal) {
        List<ProjectStudent> projects = projectStudentService.getProjectByUsername(principal.getName());
        return projects;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject (String project_name, String[] users) {
        projectStudentService.createProject(project_name,users);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been created");

    }

    @PutMapping("/projects")
    public ResponseEntity<?> editProject (Principal principal,@RequestBody ProjectStudent projectStudent) {
        projectStudentService.updateProject(projectStudent,principal);
        return ResponseEntity.status(HttpStatus.OK).body("Project has been changed");

    }

    @GetMapping("/search")
    public Set<Users> searchUser(@RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName,
                                     @RequestParam(required = false) String role,
                                     @RequestParam(required = false) String school){
        Set<Users> users = service.searchUser(firstName, lastName, role, school);

        return users;


    }


}
