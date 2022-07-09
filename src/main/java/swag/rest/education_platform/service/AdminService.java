package swag.rest.education_platform.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    public void deleteUser(Long id) {
        //todo check why find request INT instead of LONG
        userService.deleteById(id);
    }
}
