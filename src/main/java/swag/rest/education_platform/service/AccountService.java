package swag.rest.education_platform.service;

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
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.dto.UserDto;
import swag.rest.education_platform.dto.UserReponseDto;
import swag.rest.education_platform.entity.Avatar;
import swag.rest.education_platform.entity.UserFullDetails;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.UserExistException;
import swag.rest.education_platform.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public String authenticate(UserDto userDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userRepository.findByUsername(userDto.getUsername()).get();
        String jwt = JwtUtil.createAccessToken(userDto.getUsername(), request.getRequestURL().toString(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return jwt;
    }

    public void register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new UserExistException();
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        userRepository.save(user);

    }

    @Transactional
    public void updateProfileImage(MultipartFile file, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User has not been found"));
        UserFullDetails fullDetails = user.getFullDetails();
        Avatar avatar = fullDetails.getAvatar();
        if(avatar == null) avatar = new Avatar();
        avatar.setName(file.getOriginalFilename());
        try {
            avatar.setPicByte(compressByte(file.getBytes()));
            avatar.setType(file.getContentType());
            avatar.setUserFullDetails(fullDetails);
            avatarRepository.save(avatar);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Avatar getImage(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User has not been found"));
        if(user.getFullDetails().getAvatar() == null) return null;
        Avatar avatar = user.getFullDetails().getAvatar();
        Avatar result = new Avatar();
        result.setId(avatar.getId());
        result.setType(avatar.getType());
        result.setName(avatar.getName());
        result.setPicByte(decompressByte(avatar.getPicByte()));

        return result;

    }

    public static byte[] compressByte(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while(!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer,0,count);
        }
        try {
            outputStream.close();
        }
        catch (IOException e){

        }
        return outputStream.toByteArray();

    }

    public static  byte[] decompressByte(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer,0,count);
            }
            outputStream.close();
        }
        catch (IOException e) {
        }
        catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    @Transactional(readOnly = true)
    public List<UserReponseDto> getUsers() {
       List<UserReponseDto> response = new ArrayList<>();
        List<Users> users = userRepository.findAll();
        for(Users u : users) {
            UserReponseDto dto = new UserReponseDto();
            dto.setId(u.getId());
            dto.setFirstname(u.getFirstname());
            dto.setLastname(u.getLastname());
            dto.setUsername(u.getUsername());
            response.add(dto);
        }
        return response;
    }

}
