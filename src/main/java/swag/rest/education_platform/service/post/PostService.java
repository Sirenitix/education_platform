package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.PostCommentRepository;
import swag.rest.education_platform.dao.PostRepository;
import swag.rest.education_platform.dto.GeneralPostDto;
import swag.rest.education_platform.dto.PostRequestDto;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.PostComment;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Setter
public class PostService {
    private final PostRepository repository;
    String baseUrl = "http://159.89.104.8:8022/general";

    private final UserService userService;
    private final PostCommentRepository commentRepository;

    @Transactional
    public void createPost(PostRequestDto dto,String username) throws IOException {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Post post = new Post();
        post.setLikes(0L);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        post.setUser(user);
        if(dto.getFile() != null){
            post.setFile(dto.getFile().getBytes());
        }
        if(dto.getImage() != null){
            post.setImage(dto.getImage().getBytes());
        }
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
    public Post getPostById(Long id) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
//        Pageable paging = PageRequest.of(page, 50);
        List<Post> pagePost = repository.findAll();
        for (Post post : pagePost) {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/postFile/" + post.getId());
            }
            if (post.getImage() != null) {
                post.setImageLink(baseUrl + "/postImage/" + post.getId());
            }
        }


        Collections.sort(pagePost);
        return pagePost;
    }

    @Transactional(readOnly = true)
    public List<Post> getUserPosts(int page, String username) {
        Pageable paging = PageRequest.of(page, 50);
        List<Post> pagePost = repository.findAll(paging).getContent();
        pagePost = pagePost.stream().filter(s -> s.getUser().getUsername().equals(username)).collect(Collectors.toList());
        pagePost.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/postFile/" + post.getId());
            }
        });
        pagePost.forEach((post) ->
        {
            if (post.getFile() != null) {
                post.setImageLink(baseUrl + "/postImage/" + post.getId());
            }
        });
        Collections.sort(pagePost);
        return pagePost;
    }

    @Transactional(readOnly = true)
    public List<Post> getUserPostsBySchool(int page, String school) {
        Pageable paging = PageRequest.of(page, 50);
        List<Post> pagePost = repository.findAll(paging).getContent();
        pagePost = pagePost.stream().filter(s -> s.getUser().getFullDetails().getSchool().equals(school)).collect(Collectors.toList());
        pagePost.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/postFile/" + post.getId());
            }
        });
        pagePost.forEach((post) ->
        {
            if (post.getFile() != null) {
                post.setImageLink(baseUrl + "/postImage/" + post.getId());
            }
        });
        Collections.sort(pagePost);
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

    public Set<Post> searchPost(String title, String content) {
        Set<Post> result;// = repository.findAllByTitleContaining(title);
        if(title.isEmpty() && content.isEmpty()) {
            List<Post> posts = repository.findAll();
            Collections.sort(posts);
            return new HashSet<>(posts);
        }


        result = new HashSet<>(repository.findAll());
        if(!title.equals(""))  {
            result = result.stream().filter(u -> u.getTitle().contains(title)).collect(Collectors.toSet());
        }
        if(!content.equals("")) {
            result = result.stream().filter(u -> u.getContent().contains(content)).collect(Collectors.toSet());
        }
        return result;
    }


}
