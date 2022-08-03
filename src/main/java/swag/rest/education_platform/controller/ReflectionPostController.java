package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dao.PostResponseDto;
import swag.rest.education_platform.dto.DtoForPost;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.service.post.ReflectionPostService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reflection")
public class ReflectionPostController {

    private final ReflectionPostService service;

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody ReflextionPostCreateDto dto, Principal principal ) {
        service.createPost(dto, principal.getName() );
        return ResponseEntity.status(HttpStatus.CREATED).body("Post has been saved");
    }

    @GetMapping("/post/{id}")
    public ReflectionPost getPostById(@PathVariable Long id) {
        ReflectionPost post = service.getPostByIdWithComment(id);
        return post;
    }

    @PostMapping("/post/{post_id}")
    public ResponseEntity<String> updatePostById(@PathVariable Long post_id,
                                                 @RequestBody ReflextionPostCreateDto dto, Principal principal) {
        service.updatePost(dto, post_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Updated post");
    }

    @GetMapping("/currentUser/posts")
    public List<?> getAllPosts(@RequestParam(defaultValue = "0") int page, Principal principal) {
        //todo Content is also send, need to remove it from response
        List<PostResponseDto> posts = service.getCurrentUserPosts(page,principal.getName());

        return posts;
    }

    @GetMapping("/all/posts")
    public List<?> getUserPosts(@RequestParam(defaultValue = "0") int page) {
        //todo Content is also send, need to remove it from response
        List<PostResponseDto> posts = service.getAllPosts(page);

        return posts;
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

    @GetMapping("/search")
    public List<DtoForPost> searchPost(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false,defaultValue = "") String content) {
        List<DtoForPost> response = new ArrayList<>();
        Set<ReflectionPost> posts = service.searchPost(title,content);
        for(ReflectionPost post : posts) {
            DtoForPost dtoForPost = new DtoForPost();
            dtoForPost.setId(post.getId());
            dtoForPost.setOwnerId(post.getUser().getId());
            dtoForPost.setOwnerName(post.getUser().getFirstname());
            dtoForPost.setOwnerLastname(post.getUser().getLastname());
            response.add(dtoForPost);
        }
        return response;
    }

}
