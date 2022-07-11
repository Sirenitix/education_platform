package swag.rest.education_platform.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.SciencePostCommentRepository;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.post.ReflectionPostCommentService;
import swag.rest.education_platform.service.post.RefleсtionPostService;
import swag.rest.education_platform.service.post.SciencePostCommentService;
import swag.rest.education_platform.service.post.SciencePostService;

@Service
@RequiredArgsConstructor
public class AdminServiceIml implements AdminService {

    private final UserService userService;

    private final RefleсtionPostService refleсtionPostService;
    private final SciencePostService sciencePostService;

    private final ReflectionPostCommentService reflectionPostCommentService;
    private final SciencePostCommentService sciencePostCommentService;

    @Override
    public void deleteUser(Long id) {
        //todo check why find request INT instead of LONG
        userService.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        userService.deleteById(user.getId());
    }

    @Override
    public void deleteSciencePostById(Long postId) {
        SciencePost post = sciencePostService.findById(postId);
        sciencePostService.deletePost(post.getId());
    }

    @Override
    public void deleteReflectionPostById(Long postId) {
        ReflectionPost post = refleсtionPostService.findById(postId);
        sciencePostService.deletePost(post.getId());
    }

    @Override
    public void deleteScienceCommentById(Long scienceCommentId) {
       if(!sciencePostCommentService.exist(scienceCommentId)) {
           throw new RuntimeException("Comment not found");
       }
        sciencePostCommentService.deleteComment(scienceCommentId);
    }

    @Override
    public void deleteReflectionCommentById(Long reflectionCommentId) {

        if(!reflectionPostCommentService.exist(reflectionCommentId)) {
            throw new RuntimeException("Comment not found");
        }
        reflectionPostCommentService.deleteComment(reflectionCommentId);
    }
}
