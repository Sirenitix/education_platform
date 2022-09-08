package swag.rest.education_platform.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.service.post.ReflectionPostCommentService;
import swag.rest.education_platform.service.post.ReflectionPostService;
import swag.rest.education_platform.service.post.CommentService;
import swag.rest.education_platform.service.post.PostService;

@Service
@RequiredArgsConstructor
public class AdminServiceIml implements AdminService {

    private final UserService userService;

    private final ReflectionPostService reflectionPostService;

    private final PostService sciencePostService;

    private final ReflectionPostCommentService reflectionPostCommentService;

    private final CommentService postCommentService;

    private final ProjectStudentService projectStudentService;

    private  final ProjectMessageService projectMessageService;

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
        Post post = sciencePostService.findById(postId);
        sciencePostService.deletePost(post.getId());
    }

    @Override
    public void deleteReflectionPostById(Long postId) {
        ReflectionPost post = reflectionPostService.findById(postId);
        reflectionPostService.deletePost(post.getId());
    }

    @Override
    public void deleteScienceCommentById(Long scienceCommentId) {
       if(!postCommentService.exist(scienceCommentId)) {
           throw new RuntimeException("Comment not found");
       }
        postCommentService.deleteComment(scienceCommentId);
    }

    @Override
    public void deleteReflectionCommentById(Long reflectionCommentId) {

        if(!reflectionPostCommentService.exist(reflectionCommentId)) {
            throw new RuntimeException("Comment not found");
        }
        reflectionPostCommentService.deleteComment(reflectionCommentId);
    }

    @Override
    public void deleteProjectPostById(Long postId) {
        projectMessageService.deleteById(postId);
    }
}
