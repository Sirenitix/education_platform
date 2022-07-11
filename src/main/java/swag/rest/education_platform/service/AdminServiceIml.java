package swag.rest.education_platform.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostNotFoundException;

@Service
@RequiredArgsConstructor
public class AdminServiceIml implements AdminService {

    private final UserService userService;

    private  final PostService postService;

    private final CommentService commentService;

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
        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        postService.deleteById(post.getId());
    }

    @Override
    public void deleteReflectionPostById(Long refPosrId) {
        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        postService.deleteById(post.getId());
    }

    @Override
    public void deleteScienceCommentById(Long scienceCommentId, Long postId) {
        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        commentService.deleteById(post.getCommentId());
    }

    @Override
    public void deleteReflectionCommentById(Long reflectionCommentId, Long postId) {
        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        commentService.deleteById(post.getCommentId());
    }
}
