package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.entity.CustomUserDetailsService;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.UserExistException;
import swag.rest.education_platform.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String authenticate(UserDto userDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.findByUsername(userDto.getUsername()).get();
        String jwt = JwtUtil.createAccessToken(userDto.getUsername(), request.getRequestURL().toString(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return jwt;
    }

    public void register(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername()))
            throw new UserExistException();
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);

    }
}
