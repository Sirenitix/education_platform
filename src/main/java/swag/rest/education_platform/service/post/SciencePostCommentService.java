package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.ReflectionPostCommentRepository;
import swag.rest.education_platform.dao.SciencePostCommentRepository;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.entity.*;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SciencePostCommentService {
    private final SciencePostCommentRepository repository;
    private final UserRepository userRepository;
    private final SciencePostService postService;

    public void createComment(String content, Long postId, String username) {
        Users user =  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        SciencePost post = postService.findById(postId);
        SciencePostComment comment = new SciencePostComment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        repository.save(comment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
