package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.entity.AdminCredentials;
import swag.rest.education_platform.entity.Avatar;
import swag.rest.education_platform.entity.UserFullDetails;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountRestController  {

    private final AccountService service;


    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody UserDto user) {
        service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
    }

    @PostMapping("/set-profile-image")
    public ResponseEntity<String> updateProfilePicture(@RequestParam(name = "image") MultipartFile file, Principal principal){
        service.updateProfileImage(file,principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Image has been updated");
    }

    @GetMapping("/avatar/{id}")
    public Avatar getProfilePicture(@PathVariable Long id) {
        Avatar image = service.getImage(id);
        return image;

    }



    @Operation(description = "Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody Users user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }


    @PostMapping("/authenticate")
    public Map<String,String> authenticateUser(@Valid @RequestBody UserDto user, HttpServletRequest request) {
        String jwt =  service.authenticate(user,request);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwt);
        return token;
    }


    @Operation(description = "Logout")
    @PostMapping("/logout")
    public void fakeLogout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @Operation(description = "Rated student list")
    @GetMapping("/admin")
    public String aboutAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminCredentials adminCredentials = new AdminCredentials(
                auth.getPrincipal().toString(),
                auth.getAuthorities().toString().equals("[ROLE_ADMIN]"));
            return auth.getAuthorities().toString();
    }



}
