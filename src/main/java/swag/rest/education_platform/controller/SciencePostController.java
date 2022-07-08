package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.SciencePostRequestDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;
import swag.rest.education_platform.service.post.SciencePostService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/science")
public class SciencePostController {
        private final SciencePostService service;
    @PostMapping("create-post")
    public ResponseEntity<String> createPost(@RequestBody SciencePostRequestDto dto) {
        service.createPost(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been saved");
    }
    @GetMapping("post/{id}")
    public ResponseEntity<SciencePost> getPostById(@PathVariable Long id) {
        SciencePost post = service.getPostByIdWithComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page) {
        //todo Content is also send, need to remove it from response

        List<SciencePost> post = service.getPosts(page).stream().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
    @DeleteMapping("delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }
}
