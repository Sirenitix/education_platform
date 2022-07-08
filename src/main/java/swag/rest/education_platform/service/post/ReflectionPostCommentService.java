package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.ReflectionPostCommentRepository;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.ReflectionPostComment;
import swag.rest.education_platform.entity.Users;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ReflectionPostCommentService {
    private final ReflectionPostCommentRepository repository;
    private final UserRepository userRepository;
    private final RefleсtionPostService postService;

    public void createComment(String content, Long postId, String username) {
        Users user =  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        ReflectionPost post = postService.findById(postId);
        ReflectionPostComment comment = new ReflectionPostComment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        repository.save(comment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
