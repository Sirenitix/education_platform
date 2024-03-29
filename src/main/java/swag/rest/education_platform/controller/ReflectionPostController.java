package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dto.PostResponseDto;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.Post;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.service.post.ReflectionPostService;

import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reflection")
public class ReflectionPostController {

    private final ReflectionPostService service;

    @RequestMapping(path = "/post", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<String> createPost(@ModelAttribute ReflextionPostCreateDto dto, Principal principal) throws IOException {
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

    @GetMapping("/all/postsBySchool/{school}")
    public List<?> getUserPostsBySchool(@RequestParam(defaultValue = "0") int page, @PathVariable String school) {
        //todo Content is also send, need to remove it from response
        List<PostResponseDto> posts = service.getAllPostsBySchool(page, school);
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
    public Set<ReflectionPost> searchPost(@RequestParam(required = false, defaultValue = "") String title,
                                          @RequestParam(required = false,defaultValue = "") String content,
                                          @RequestParam(required = false,defaultValue = "") String tag) {
        Set<ReflectionPost> posts = service.searchPost(title, content, tag);
        return posts;
    }

    @GetMapping("/refPostFile/{id}")
    public ResponseEntity<?> getRefPostFileById(@PathVariable Long id) throws IOException {
        ReflectionPost post = service.findById(id);
        HttpHeaders responseheaders = new HttpHeaders();
        String contentType = URLConnection.
                guessContentTypeFromStream(new ByteArrayInputStream(post.getFile()));
        responseheaders.setContentType(MediaType.valueOf(contentType));
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(post.getFile(),responseheaders,HttpStatus.OK);
    }

    @GetMapping("/refPostImage/{id}")
    public ResponseEntity<?> getRefPostIamgeById(@PathVariable Long id) {
        ReflectionPost post = service.findById(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.IMAGE_JPEG);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(post.getImage(),responseheaders,HttpStatus.OK);
    }


}
