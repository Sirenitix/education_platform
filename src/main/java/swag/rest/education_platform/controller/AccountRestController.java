package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dto.CurrentUserDto;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.dto.UserFullDto;
import swag.rest.education_platform.dto.UserReponseDto;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.service.AccountService;
import swag.rest.education_platform.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountRestController {

    private final AccountService service;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/admin_users")
    public List<UserReponseDto> getAdminUsers() {
        List<UserReponseDto> users = service.getAdminUsers();
        return users;
    }


    @GetMapping("/users")
    public List<UserReponseDto> getUsers() {
        List<UserReponseDto> users = service.getUsers();
        return users;
    }

    @GetMapping("/users/{id}")
    public Users getUsers(@PathVariable Long id) {
        Users user = service.getUser(id);
       return user;
    }


    @GetMapping("/current-user")
    public UserFullDetails currentUser(Principal principal) {
        Users users = service.getCurrentUser(principal.getName());
        return users.getFullDetails();
    }



    @PostMapping("/activate-user/{id}")
    public ResponseEntity<?> currentUser(@PathVariable("id") Long id) {
        service.setEnableTrue(id);
        return ResponseEntity.status(HttpStatus.OK).body("User activated");
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> save(@RequestBody UserDto user) {
//        service.register(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
//    }

    @PostMapping("/full-register")
    public ResponseEntity<String> registerUserWithFullDetails(@RequestBody UserFullDetails user) {
        service.registerWithFullDetails(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
    }

    @PostMapping("/user/avatar")
    public ResponseEntity<String> updateProfilePicture(@RequestParam(name = "image") MultipartFile file, Principal principal) {
        service.updateProfileImage(file, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Image has been updated");
    }

    @GetMapping(value = "/user/avatar/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfilePicture(@PathVariable Long id) {
        Avatar avatar = service.getImage(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);
        responseHeaders.setContentDisposition(ContentDisposition.attachment().build());
        return avatar.getImage();

    }


    @PostMapping("/auth")
    public Map<String, String> authenticateUser(@Valid @RequestBody UserDto user, HttpServletRequest request) {
        String jwt = service.authenticate(user, request);
        Map<String, String> token = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        token.put("token", jwt);
        token.put("role", auth.getAuthorities().toString());
        return token;
    }


    @Operation(description = "Rated student list")
    @GetMapping("/admin")
    public String aboutAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toString();
    }


    @GetMapping("/search")
    public List<?> searchUser(@RequestParam(required = false) String firstName,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String role,
                                 @RequestParam(required = false) String school) {
        Set<Users> users = service.searchUser(firstName, lastName, role, school);
        List<UserFullDto> dtos = new ArrayList<>();
        for(Users user : users) {
            UserFullDto dto = new UserFullDto();
            dto.setId(user.getId());
            dto.setCity(user.getFullDetails().getCity());
            dto.setFirstname(user.getFirstname());
            dto.setLastname(user.getLastname());
            dto.setSchool(user.getFullDetails().getSchool());
            dto.setRole(user.getFullDetails().getTitle());
            dto.setUsername(user.getUsername());
            dtos.add(dto);
        }
        return dtos;


    }


}
