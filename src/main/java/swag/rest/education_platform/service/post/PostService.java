package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import swag.rest.education_platform.dao.PostResponseDto;
import swag.rest.education_platform.dao.PostCommentRepository;
import swag.rest.education_platform.dao.PostRepository;
import swag.rest.education_platform.dto.GeneralPostDto;
import swag.rest.education_platform.dto.PostResponseWithoutTag;
import swag.rest.education_platform.dto.PostRequestDto;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.PostComment;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class PostService {
    private final PostRepository repository;
    private final UserService userService;
    private final PostCommentRepository commentRepository;

    @Transactional
    public void createPost(PostRequestDto dto,String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Post post = new Post();
        post.setLikes(0L);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        post.setUser(user);

        repository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getPostByIdWithComment(Long id) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
        List<PostComment> comments = commentRepository.findAllById(id);
        post.setComment(comments);

        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(int page) {
        Pageable paging = PageRequest.of(page, 50);
        List<Post> pagePost = repository.findAll(paging).getContent();
        return pagePost;
    }

    @Transactional(readOnly = true)
    public List<Post> getUserPosts(int page, String username) {
        Pageable paging = PageRequest.of(page, 50);
        List<Post> pagePost = repository.findAll(paging).getContent();
        pagePost = pagePost.stream().filter(s -> s.getUser().getUsername().equals(username)).collect(Collectors.toList());
        return pagePost;
    }

    @Transactional
    public void updatePost(GeneralPostDto dto, Long post_id, String username) {
        Long user_id = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId();
        Post post = repository.findById(post_id).orElseThrow(PostNotFoundException::new);
        if (!post.getUser().getId().equals(user_id))
            throw new PostException("You are not owner of this post");
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setLikes(dto.getLikes());
        repository.save(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public List<Post> searchPost(String query) {
        List<Post> posts = repository.findAllByContentContaining(query);
        return posts;
    }
}
