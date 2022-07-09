package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.jwt.JwtUtil;
import swag.rest.education_platform.service.AccountService;
import swag.rest.education_platform.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDto user, HttpServletRequest request) {
        String jwt =  service.authenticate(user,request);
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
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
