package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dao.PostResponseDto;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.service.post.RefleсtionPostService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reflection")
public class ReflectionPostController {

    private final RefleсtionPostService service;

    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(@RequestBody ReflextionPostCreateDto dto, Principal principal) {
        service.createPost(dto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Post has been saved");
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<ReflectionPost> getPostById(@PathVariable Long id) {
        ReflectionPost post = service.getPostByIdWithComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping("/update/{post_id}")
    public ResponseEntity<String> updatePostById(@PathVariable Long post_id,
                                                 @RequestBody ReflextionPostCreateDto dto, Principal principal) {
        service.updatePost(dto, post_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Updated post");
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "1") int page) {
        //todo Content is also send, need to remove it from response
        List<PostResponseDto> posts = service.getPosts(page);
        System.out.println(posts.size());

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

}
