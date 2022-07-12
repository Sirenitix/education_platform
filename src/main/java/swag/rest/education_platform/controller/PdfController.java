package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.service.PdfMaterialService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PdfController {
        private final PdfMaterialService service;
    @GetMapping
    public ResponseEntity<?> getPdfs() {
        List<PdfMaterial> documents = service.getDocument();
        return ResponseEntity.status(HttpStatus.OK).body(documents);
    }

    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam MultipartFile file, Principal principal, String[] tags) {
        service.saveDocument(file,principal.getName(),tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }
}
