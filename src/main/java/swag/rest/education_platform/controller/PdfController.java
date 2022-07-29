package swag.rest.education_platform.controller;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.MarkupConvertOptions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.service.PdfMaterialService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @GetMapping(value = "/html/{id}")
    public ResponseEntity getHtml(@PathVariable Long id) throws IOException {
        PdfMaterial document = service.getDocumentById(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        OutputStream out = Files.newOutputStream(Paths.get("/root/out.pdf"));
        out.write(document.getContent());
        out.close();
        Converter converter = new Converter("/root/out.pdf");
        MarkupConvertOptions options = new MarkupConvertOptions();
        options.setFixedLayout(true);
        options.setPageNumber(1);
        String outputFile =  "/root/sample.html";
        converter.convert(outputFile, options);
        File file = new File("/root/sample.html");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        return  new ResponseEntity(bytes,responseheaders,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> uploadPdf(@RequestParam MultipartFile file, Principal principal, String[] tags) {
        service.saveDocument(file,principal.getName(),tags);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }



}
