package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.jwt.JwtUtil;
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

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<Users> save(@RequestBody Users user) {
        Users userEntity = userService.save(user);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{username}")
                .buildAndExpand(userEntity.getUsername()).toUriString());
        return ResponseEntity.created(uri).build();
    }



    @Operation(description = "Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody Users user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }


    @PostMapping("/authenticate")
    public String authenticateUser(@Valid @RequestBody Users user, HttpServletRequest request, HttpServletResponse response) {
        user.setRole("ROLE_USER");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createAccessToken(user.getUsername(), request.getRequestURL().toString(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        ResponseCookie cookie = ResponseCookie.from("token", jwt) // key & value
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofHours(100))
                .sameSite("None")  // sameSite
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return jwt;
    }


    @Operation(description = "Logout")
    @PostMapping("/logout")
    public void fakeLogout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @Operation(description = "Rated student list")
    @GetMapping("/admin")
    public AdminCredentials aboutAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminCredentials adminCredentials = new AdminCredentials(
                auth.getPrincipal().toString(),
                auth.getAuthorities().toString().equals("[ROLE_ADMIN]"));
            return adminCredentials;
    }



}
