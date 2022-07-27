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
import swag.rest.education_platform.dao.PostResponseDto;
import swag.rest.education_platform.dao.SciencePostCommentRepository;
import swag.rest.education_platform.dao.SciencePostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.dto.SciencePostRequestDto;
import swag.rest.education_platform.entity.*;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SciencePostService {
    private final SciencePostRepository repository;
    private final UserService userService;
    private final SciencePostCommentRepository commentRepository;

    @Transactional
    public void createPost(SciencePostRequestDto dto,String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        SciencePost post = new SciencePost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        post.setUser(user);

        repository.save(post);
    }

    @Transactional(readOnly = true)
    public SciencePost getPostByIdWithComment(Long id) {
        SciencePost post = repository.getOnlyPostById(id);
        List<SciencePostComment> comments = commentRepository.getOnlyCommentByPostId(id);
        post.setComment(comments);

        return post;
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(int page) {
        Pageable paging = PageRequest.of(page, 50);
        List<SciencePost> pagePost = repository.findAll(paging).getContent();
        List<PostResponseDto> dto = new ArrayList<>();
        for (SciencePost post : pagePost) {
            if (post.getContent().length() > 100) {
                post.setContent(post.getContent().substring(0, 99));
            }
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getId());
            response.setContent(post.getContent());
            response.setTitle(post.getTitle());
            response.setUsername(post.getUser().getUsername());
            dto.add(response);
        }
        return dto;
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

    public List<SciencePost> searchPost(String query) {
        List<SciencePost> posts = repository.findAllByContentContaining(query);
        return posts;
    }
}
