package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dao.PdfMaterialRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Users;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PdfMaterialService {

    private final PdfMaterialRepository repository;
    private final UserService userService;

    public void saveDocument(MultipartFile file, String username, String[] tags) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PdfMaterial pdf = new PdfMaterial();
        pdf.setTitle(file.getOriginalFilename());
        pdf.setType(file.getContentType());
        pdf.setDate(LocalDate.now());
        try {
            pdf.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pdf.setTag("TEST");
        pdf.setUser(user);
        repository.save(pdf);
    }
}
