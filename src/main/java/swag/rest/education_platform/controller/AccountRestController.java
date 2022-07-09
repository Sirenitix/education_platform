package swag.rest.education_platform.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import swag.rest.education_platform.dto.UserRegisterDto;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.exception.UserExistException;
import swag.rest.education_platform.jwt.JwtUtil;
import swag.rest.education_platform.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AccountRestController  {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody UserRegisterDto user) {
        userService.findByUsername(user.getUsername()).orElseThrow(() -> new UserExistException());
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));

        Users userEntity = userService.save(newUser);
//        URI uri = URI.create(ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{username}")
//                .buildAndExpand(userEntity.getUsername()).toUriString());
//        return ResponseEntity.created(uri).build();
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered");
    }



    @Operation(description = "Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody Users user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

//    @PostMapping("/auth")
//    public ResponseEntity<?> createAuthToken(@RequestBody UserRegisterDto user) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");//new ResponseMessage( "Incorrect username or password",HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
//        }
//        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
//        String token = jwtTokenUtil.generateToken(userDetails);
//        List<String> roles = jwtTokenUtil.getRoles(token);
//
//        return ResponseEntity.ok(new JwtResponse(token, roles));
//    }

    @PostMapping("/authenticate")
    public String authenticateUser(@Valid @RequestBody Users user, HttpServletRequest request, HttpServletResponse response) {
        user.setRole("ROLE_USER");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtUtil.createAccessToken(user.getUsername(), request.getRequestURL().toString(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
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
