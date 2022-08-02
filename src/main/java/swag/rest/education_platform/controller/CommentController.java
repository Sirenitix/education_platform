package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.service.post.CommentService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/post-comment")
public class CommentController {
    private final CommentService service;
    @PostMapping()
    public ResponseEntity<?> createComment(@RequestParam String content, @RequestParam Long postId, Principal principal ) {
        service.createComment(content,postId,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment has been added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Comment has been deleted");
    }
}
