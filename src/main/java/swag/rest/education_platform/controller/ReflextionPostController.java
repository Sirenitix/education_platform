package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reflextion")
public class ReflextionPostController {


    @PutMapping("create-post")
    public ResponseEntity<String> createPost() {

        return ResponseEntity.status(HttpStatus.OK).body("Post has been saved");
    }

    @DeleteMapping("delete-post")
    public ResponseEntity<String> deletePost() {

        return ResponseEntity.status(HttpStatus.OK).body("Post has been deleted");
    }

}
