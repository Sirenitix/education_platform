package swag.rest.education_platform.controller;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.MarkupConvertOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.service.PdfMaterialService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PdfController {
        private final PdfMaterialService service;

    @GetMapping(value = "/{id}")
    public byte[] getPdf(@PathVariable Long id) {
        PdfMaterial document = service.getDocumentById(id);
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setContentType(MediaType.APPLICATION_PDF);
        responseheaders.setContentDisposition(ContentDisposition.inline().build());
        return document.getContent();//new ResponseEntity(document.getContent(),responseheaders,HttpStatus.OK);
    }

//    @GetMapping()
//    public List<PdfMaterial> getPdfs() {
//        return service.getPdfs();
//    }

//    @GetMapping(value = "/html/{id}")
//    public byte[] getHtml(@PathVariable Long id) throws IOException {
////
//        PdfMaterial document = service.getDocumentById(id);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_PDF);
//        responseHeaders.setContentDisposition(ContentDisposition.inline().build());
//        OutputStream out = Files.newOutputStream(Paths.get("tmp/out.pdf"));
//        out.write(document.getContent());
//        out.close();
//        Converter converter = new Converter("tmp/out.pdf");
//        MarkupConvertOptions options = new MarkupConvertOptions();
//        options.setFixedLayout(true);
//        options.setPageNumber(1);
//        String outputFile =  "tmp/warning.html";
//        converter.convert(outputFile, options);
//        File file = new File("tmp/warning.html");
//        byte[] bytes = new byte[(int) file.length()];
//        try(FileInputStream fis = new FileInputStream(file)){
//            fis.read(bytes);
//        }
//        return bytes;
//    }


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
        List<PdfMaterial> pdfs = service.getPdfs();
        return pdfs;
    }



}
