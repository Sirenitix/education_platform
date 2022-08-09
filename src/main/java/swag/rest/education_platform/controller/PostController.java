package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.dto.DtoForPost;
import swag.rest.education_platform.dto.GeneralPostDto;
import swag.rest.education_platform.dto.PostRequestDto;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.service.post.PostService;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/general")
public class PostController {
        private final PostService service;

    @RequestMapping(path = "/post", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<String> createPost(@ModelAttribute PostRequestDto dto, Principal principal) throws IOException {
        service.createPost(dto, principal.getName() );
        return ResponseEntity.status(HttpStatus.OK).body("Post has been saved");
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = service.getPostByIdWithComment(id);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
//Hey
    @GetMapping("/postFile/{id}")
    public ResponseEntity getPostFileById(@PathVariable Long id) {
        Post post = service.getPostByIdWithComment(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return ResponseEntity.status(HttpStatus.OK).body(post.getFile());
    }

    @GetMapping("/postImage/{id}")
    public ResponseEntity getPostIamgeById(@PathVariable Long id) {
        Post post = service.getPostByIdWithComment(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return ResponseEntity.status(HttpStatus.OK).body(post.getImage());
    }

    //hey

    @PostMapping("/post/{post_id}")
    public ResponseEntity<String> updatePostById(@PathVariable Long post_id,
                                                 @RequestBody GeneralPostDto dto, Principal principal) {
        service.updatePost(dto, post_id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Updated post");
    }

    @GetMapping("/allPosts")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllPosts(page));
    }


    @GetMapping("/getUserPosts")
    public ResponseEntity<?> getUserPosts(@RequestParam(defaultValue = "0") int page, Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserPosts(page, principal.getName()));
    }

    @GetMapping("/getUserPosts/{school}")
    public ResponseEntity<?> getUserPostsBySchool(@RequestParam(defaultValue = "0") int page, Principal principal, @PathVariable String school) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserPostsBySchool(page, principal.getName(), school));
    }



    @DeleteMapping("/post{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

    @GetMapping("/search")
    public Set<Post> search(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false,defaultValue = "") String content) {
        Set<Post> posts = service.searchPost(title,content);
        return posts;
    }



}
