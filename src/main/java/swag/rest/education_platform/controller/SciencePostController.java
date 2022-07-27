package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.DtoForPost;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.dto.SciencePostRequestDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;
import swag.rest.education_platform.service.post.SciencePostService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/science")
public class SciencePostController {
        private final SciencePostService service;
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody SciencePostRequestDto dto, Principal principal) {
        service.createPost(dto, principal.getName() );
        return ResponseEntity.status(HttpStatus.OK).body("Post has been saved");
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<SciencePost> getPostById(@PathVariable Long id) {
        SciencePost post = service.getPostByIdWithComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
    @PostMapping("/post/{post_id}")
    public ResponseEntity<String> updatePostById(@PathVariable Long post_id,
                                                 @RequestBody SciencePostRequestDto dto, Principal principal) {
        service.updatePost(dto, post_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Updated post");
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPosts(page));
    }
    @DeleteMapping("/post{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

    @GetMapping("/search")
    public List<DtoForPost> searchPost(@RequestParam String query) {
        List<DtoForPost> response = new ArrayList<>();
        List<SciencePost> posts = service.searchPost(query);
        for (SciencePost post : posts) {
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
