package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.GlobalAnnotationRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.entity.annotation.Annotation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnotationService {
    private final GlobalAnnotationRepository repository;
    private final PdfMaterialService pdfService;
    private final UserService userService;

    @Transactional
    public void addGlobalAnnotation(Long pdfId, String annotation) {
        Annotation anno = new Annotation();
        anno.setContent(annotation);
        anno.setUser(null);
        anno.setPdf(pdfService.getPdf(pdfId));
        repository.save(anno);
    }

    @Transactional(readOnly = true)
    public List<Annotation> getGlobalAnnotation(Long pdfId) {
        PdfMaterial pdfMaterial = pdfService.getPdf(pdfId);
        return repository.findAllByPdf(pdfMaterial);
    }


    @Transactional
    public void addPrivateAnnotation(Long pdfId, String annotation, String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Annotation anno = new Annotation();
        anno.setContent(annotation);
        anno.setUser(user);
        anno.setPdf(pdfService.getPdf(pdfId));
        repository.save(anno);
    }

    @Transactional(readOnly = true)
    public List<Annotation> getPrivateAnnotation(Long pdfId, String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PdfMaterial pdfMaterial = pdfService.getPdf(pdfId);
        return repository.findAllByPdfAndUser(pdfMaterial, user);
    }

}
