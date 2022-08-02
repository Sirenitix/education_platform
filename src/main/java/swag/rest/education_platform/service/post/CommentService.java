package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.PostCommentRepository;
import swag.rest.education_platform.dao.UserRepository;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.PostComment;
import swag.rest.education_platform.entity.Users;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class CommentService {
    private final PostCommentRepository repository;
    private final UserRepository userRepository;
    private final PostService postService;

    public void createComment(String content, Long postId, String username) {
        Users user =  userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        Post post = postService.findById(postId);
        PostComment comment = new PostComment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);
        repository.save(comment);
    }

    public Boolean exist(Long id) {
       return repository.existsById(id);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }


}
