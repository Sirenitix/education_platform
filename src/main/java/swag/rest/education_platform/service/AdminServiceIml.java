package swag.rest.education_platform.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.entity.Users;

@Service
@RequiredArgsConstructor
public class AdminServiceIml implements AdminService {

    private final UserService userService;

    @Override
    public void deleteUser(Long id) {
        //todo check why find request INT instead of LONG
        userService.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        Users user = userService.findByUsername(username).orElse(null);
        assert user != null;
        userService.deleteById(user.getId());
    }

    @Override
    public void deleteSciencePostById(Long postId) {

    }

    @Override
    public void deleteReflectionPostById(Long refPosrId) {

    }

    @Override
    public void deleteScienceCommentById(Long scienceCommentId) {

    }

    @Override
    public void deleteReflectionCommentById(Long reflectionCommentId) {

    }
}
