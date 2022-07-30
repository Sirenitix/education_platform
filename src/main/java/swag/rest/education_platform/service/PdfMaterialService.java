package swag.rest.education_platform.service;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.MarkupConvertOptions;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dao.PdfMaterialRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Users;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class PdfMaterialService {

    private final PdfMaterialRepository repository;
    private final UserService userService;

    @Transactional
    public void saveDocument(MultipartFile file, String username, String[] tags) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PdfMaterial pdf = new PdfMaterial();
        pdf.setTitle(file.getOriginalFilename());
        pdf.setType(file.getContentType());
        pdf.setDate(LocalDate.now());
//        try {
//            pdf.setContent(file.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        pdf.setTag("TEST");
                pdf.setUser(null);

        Converter converter = null;
        byte[] htmlToDb;
        try {

//            OutputStream out = Files.newOutputStream(Paths.get("html/out.pdf"));
//            out.write(file.getBytes());
//            out.close();
//            String testFile = "html/test.pdf";
            converter = new Converter(file.getInputStream());
//            converter = new Converter(testFile);
            String outputFile =  "html/warning.html";
            MarkupConvertOptions options = new MarkupConvertOptions();
            options.setFixedLayout(true);
            options.setPageNumber(3);
            options.setPagesCount(100);
            converter.convert(outputFile, options);
            //converting html to bytes

            File tempFile = new File("html/warning.html");
            htmlToDb = new byte[(int) tempFile.length()];
            FileInputStream fis = new FileInputStream(tempFile);
            fis.read(htmlToDb);
            //todo fix close
            fis.close();

            pdf.setContent(htmlToDb);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repository.save(pdf);
    }

    @Transactional(readOnly = true)
    public PdfMaterial getDocumentById(Long id) {

        PdfMaterial pdf = repository.getonlypdfbyid(id).orElseThrow(() -> new RuntimeException("Not found"));
        return pdf;
    }


}
