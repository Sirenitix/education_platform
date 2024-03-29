package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.UserPdfLibrary;
import swag.rest.education_platform.service.PdfMaterialService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PdfController {
        private final PdfMaterialService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getPdf(@PathVariable Long id) {
        PdfMaterial document = service.getDocumentById(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_PDF);
        responseHeaders.setContentDisposition(ContentDisposition.inline().build());
        return new ResponseEntity<>(document.getContent(),responseHeaders,HttpStatus.OK);
    }

    @PostMapping("/library")
    public ResponseEntity<Object> addToList(@RequestParam Long id, Principal principal){
        service.addToList(id,principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/library")
    public ResponseEntity<Object> deleteFromList(@RequestParam Long id, Principal principal){
        service.removeFromList(id,principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/library")
    public List<PdfMaterial> getLibrary(Principal principal) {

        return service.getLibrary(principal.getName());
    }

    @GetMapping("/getPdfListByTagName/{tagName}")
    public List<PdfMaterial> getLibrary(Principal principal,@PathVariable String tagName) {
        return service.getLibraryByTag(principal.getName(),tagName);
    }

    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam MultipartFile file, Principal principal, @RequestParam String[] tags) {

        service.saveDocument(file,principal.getName(),tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @PutMapping
    public ResponseEntity<String> updatePdf(@RequestParam MultipartFile file, @RequestParam Long id) {
        service.updatePdf(file,id);
        return ResponseEntity.status(HttpStatus.OK).body("Updated PDF");
    }

    @GetMapping
    public List<PdfMaterial> getTitles() {
        return service.getPdfs();
    }




}
