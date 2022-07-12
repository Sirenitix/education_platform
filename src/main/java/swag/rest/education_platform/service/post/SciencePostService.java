package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.SciencePostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.dto.SciencePostRequestDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SciencePostService {
    private final SciencePostRepository repository;
    private final UserService userService;

    @Transactional
    public void createPost(SciencePostRequestDto dto) {
        SciencePost post = new SciencePost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        repository.save(post);
    }

    @Transactional(readOnly = true)
    public SciencePost getPostByIdWithComment(Long id) {
        return repository.getPostByIdWithComment(id).orElseThrow(PostNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<SciencePost> getPosts(int page) {
        Pageable paging = PageRequest.of(page, 50);
        List<SciencePost> pagePost = repository.findAll(paging).getContent();
        for (SciencePost post : pagePost) {
            if (post.getContent().length() > 100) {
                post.setContent(post.getContent().substring(0, 99));
            }
        }
        return pagePost;
    }

    @Transactional
    public void updatePost(SciencePostRequestDto dto, Long post_id, String username) {
        Long user_id = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId();
        SciencePost post = repository.findById(post_id).orElseThrow(PostNotFoundException::new);
        if (!post.getUser().getId().equals(user_id))
            throw new PostException("You are not owner of this post");
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        repository.save(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public SciencePost findById(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }
}
