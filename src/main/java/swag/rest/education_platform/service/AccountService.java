package swag.rest.education_platform.service;

import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dao.AvatarRepository;
import swag.rest.education_platform.dao.UserFullDetailsRepository;
import swag.rest.education_platform.dao.UserPdfLibraryRepository;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.dto.RegisterUserDto;
import swag.rest.education_platform.dto.UserFullDto;
import swag.rest.education_platform.dto.UserReponseDto;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.exception.UserExistException;
import swag.rest.education_platform.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final UserFullDetailsRepository userFullDetailsRepository;
    private final AvatarRepository avatarRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final ProjectStudentService studentService;
    private final UserPdfLibraryRepository libraryRepository;



    public String authenticate(UserDto userDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.findByUsername(userDto.getEmail()).orElseThrow(UserExistException::new);
        String jwt = JwtUtil.
                createAccessToken(userDto.getEmail(), request.getRequestURL().toString(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        return jwt;
    }

    public void register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getEmail()))
            throw new UserExistException();
        Users user = new Users();
        user.setUsername(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);

    }

    @Transactional
    public void registerWithFullDetails(RegisterUserDto dto) {
        if (userRepository.existsByUsername(dto.getEmail()))
            throw new UserExistException();
        Users user = new Users();
        user.setUsername(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setEnabled(false);
        //todo role is selected at registration
        user.setRole("ROLE_USER");
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        UserPdfLibrary library = new UserPdfLibrary();
        library.setPdf(new ArrayList<>());
        library.setUser(user);
        user.setLibraries(library);
        UserFullDetails fullDetails = new UserFullDetails();
        fullDetails.setSchool(dto.getSchool());
        fullDetails.setUser(user);
        fullDetails.setCity(dto.getCity());
        fullDetails.setTitle(dto.getTitle());
        fullDetails.setFirstname(dto.getFirstname());
        fullDetails.setLastname(dto.getLastname());
        fullDetails.setEmail(dto.getEmail());
        fullDetails.setRole(user.getRole());
        userRepository.save(user);
        userFullDetailsRepository.save(fullDetails);
        libraryRepository.save(library);
    }

    @Transactional
    public void updateProfileImage(MultipartFile file, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User has not been found"));
        UserFullDetails fullDetails = user.getFullDetails();
        Avatar avatar = user.getFullDetails().getAvatar();
        if(user.getFullDetails().getAvatar() == null) {
            avatar = new Avatar();
            avatar.setUserFullDetails(fullDetails);
        }
        String extension = Files.getFileExtension(file.getOriginalFilename());
       avatar.setName(user.getId() + "_avatar." + extension);
       avatar.setType(file.getContentType());
        try {
            avatar.setImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        avatarRepository.save(avatar);


    }

    public Avatar getImage(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User has not been found"));
       return user.getFullDetails().getAvatar();

    }

    public static byte[] compressByte(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toByteArray();

    }

    public static byte[] decompressByte(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException e) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    @Transactional(readOnly = true)
    public List<UserReponseDto> getAdminUsers() {
        List<UserReponseDto> response = new ArrayList<>();
        List<Users> users = userRepository.findAll();
        for (Users u : users) {
            if (!u.isEnabled()) {
                UserReponseDto dto = new UserReponseDto();
                dto.setId(u.getId());
                dto.setFirstname(u.getFirstname());
                dto.setLastname(u.getLastname());
                dto.setEmail(u.getUsername());
                dto.setEnabled(u.isEnabled());
                dto.setImage(u.getFullDetails().getAvatar());
                response.add(dto);
            }
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<UserReponseDto> getUsers() {
        List<UserReponseDto> response = new ArrayList<>();
        List<Users> users = userRepository.findAll();
        for (Users u : users) {
            if (u.isEnabled()) {
                UserReponseDto dto = new UserReponseDto();
                dto.setId(u.getId());
                dto.setFirstname(u.getFirstname());
                dto.setLastname(u.getLastname());
                dto.setEmail(u.getUsername());
                dto.setEnabled(u.isEnabled());
                dto.setImage(u.getFullDetails().getAvatar());
                response.add(dto);
            }
        }
        return response;
    }

    @Transactional(readOnly = true)
    public Users getUser(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User has not been found"));
        List<ProjectStudent> projects = studentService.getProjectByUsername(user.getUsername());
        user.setProjects(projects);
        return user;
    }


    @Transactional(readOnly = true)
    public UserFullDetails getCurrentUser(String username) {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " - not found"));
        return users.getFullDetails();
    }

    @Transactional
    public void setEnableTrue(Long id) {
        Users users = userRepository.findById(id).orElseThrow(UserExistException::new);
        users.setEnabled(true);
        userRepository.setEnableTrue(id);
    }

    @Transactional(readOnly = true)
    public Set<Users> searchUser(String firstName, String lastName, String role, String school) {

        Set<Users> users = new HashSet<>();

        if (firstName != null) {
            users.addAll(userRepository.findAllByFirstnameContainingIgnoreCase(firstName));
        }
        if (!lastName.isEmpty()) {
            if (users.isEmpty())
                users.addAll(userRepository.findAllByLastnameContainingIgnoreCase(lastName));
            else {
                users = users.stream().filter(u -> u.getLastname().contains(lastName)).collect(Collectors.toSet());
            }

        }
        if (!role.isEmpty()) {
            if (users.isEmpty()) users.addAll(userRepository.findAllByRoleContainingIgnoreCase(role));
            else {
                users = users.stream().filter(u -> u.getRole().contains(role)).collect(Collectors.toSet());

            }
        }
        if (!school.isEmpty()) {
            if (users.isEmpty()) users.addAll(userRepository.findAllBySchool(school));
            else {
                users = users.stream().filter(u -> u.getFullDetails().getSchool().contains(school)).collect(Collectors.toSet());
            }

        }

        return users;


    }
}
