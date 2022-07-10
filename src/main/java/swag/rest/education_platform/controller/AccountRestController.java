package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.entity.AdminCredentials;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
