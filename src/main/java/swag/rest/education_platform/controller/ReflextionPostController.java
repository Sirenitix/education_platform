package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.service.post.ReflextionPostService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reflextion")
public class ReflextionPostController {

    private final ReflextionPostService service;
    @PostMapping("create-post")
    public ResponseEntity<String> createPost(@RequestBody ReflextionPostCreateDto dto) {
        service.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post has been saved");
    }

    @GetMapping("post/{id}")
    public ResponseEntity<ReflectionPost> getPostById(@PathVariable Long id) {
        ReflectionPost post = service.findPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
    @DeleteMapping("delete-post")
    public ResponseEntity<String> deletePost() {

        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

}
