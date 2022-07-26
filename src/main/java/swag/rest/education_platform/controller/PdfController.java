package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.service.PdfMaterialService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PdfController {
        private final PdfMaterialService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity getPdfs(@PathVariable Long id) {
        PdfMaterial document = service.getDocumentById(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return  new ResponseEntity(document.getContent(),responseheaders,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam MultipartFile file, Principal principal, String[] tags) {
        service.saveDocument(file,principal.getName(),tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }
}
