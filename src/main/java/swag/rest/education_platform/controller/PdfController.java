package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.service.PdfMaterialService;

import java.io.*;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PdfController {
        private final PdfMaterialService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity getPdfs(@PathVariable Long id) {
        PdfMaterial document = service.getDocumentById(id);
//        File file = new File("/Users/rahimlugma/Documents/Hackaton2/project 4 (share)/education_platform/src/main/resources/Static/pdf");
//        try {
//            OutputStream stream = new FileOutputStream(file + "/test.pdf");
//            stream.write(document.getContent());
//            stream.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        catch (IOException e) {
//
//        }
//        file = new File("/Users/rahimlugma/Documents/Hackaton2/project 4 (share)/education_platform/src/main/resources/Static/pdf/test.pdf");
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return  new ResponseEntity(document.getContent(),responseheaders,HttpStatus.OK);
       // return file;//ResponseEntity.status(HttpStatus.OK).body((Object) file);
    }

    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam MultipartFile file, Principal principal, String[] tags) {
        service.saveDocument(file,principal.getName(),tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }
}
