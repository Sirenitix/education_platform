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
import swag.rest.education_platform.dao.ReflectionPostCommentRepository;
import swag.rest.education_platform.dao.ReflectionPostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class RefleсtionPostService {

    private final ReflectionPostRepository repository;
    private final UserService userService;
    private final ReflectionPostCommentRepository commentRepository;

    @Transactional
    public void createPost(ReflextionPostCreateDto dto, String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        ReflectionPost post = new ReflectionPost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        post.setTag(dto.getTag());
        post.setUser(user);
        repository.save(post);
    }


    @Transactional(readOnly = true)
    public ReflectionPost getPostByIdWithComment(Long id) {
        ReflectionPost post = repository.findById(id).get();
        return post;
    }
    @Transactional
    public void updatePost(ReflextionPostCreateDto dto, Long post_id, String username) {
        Long user_id = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found")).getId();
        ReflectionPost post = repository.findById(post_id).orElseThrow(() -> new PostNotFoundException());
        if(!post.getUser().getId().equals(user_id))
            throw new PostException("You are not owner of this post");
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        repository.save(post);
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(int page) {

        Pageable paging = PageRequest.of(page,50);
        List<ReflectionPost> pagePost =  repository.findAll(paging).getContent();
        List<PostResponseDto> dto = new ArrayList<>();

        for (ReflectionPost post : pagePost) {

            if(post.getContent().length() >100) {
                post.setContent(post.getContent().substring(0,99));
            }
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getId());
            response.setContent(post.getContent());
            response.setTitle(post.getTitle());
            response.setUsername(post.getUser().getUsername());
            response.setTag(post.getTag());
            dto.add(response);


        }
        return dto;
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public ReflectionPost findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PostNotFoundException());
    }

    public Page<ReflectionPost> findByTag(Integer offset, Integer limit, Tag tag) {
        Pageable paging = PageRequest.of(offset,limit);

        return repository.findByTag(tag, paging);
    }
}
