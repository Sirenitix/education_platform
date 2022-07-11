package swag.rest.education_platform.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.VideoMaterial;
import swag.rest.education_platform.service.VideoMaterialService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/video")
public class VideoMaterialController {

    private final VideoMaterialService service;
    @GetMapping("{tag}")
    public ResponseEntity<?> getVideoByTag(@PathVariable String tag, int page) {
        List<VideoMaterial> videos = service.getVideoByTag(tag, page);
        return ResponseEntity.status(HttpStatus.OK).body(videos);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVideoById(@PathVariable Long id, Principal principal) {
        service.deleteVideoById(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted video with id: " + id);
    }
}
