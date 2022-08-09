package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swag.rest.education_platform.dao.PdfMaterialRepository;
import swag.rest.education_platform.dao.UserPdfLibraryRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.entity.UserPdfLibrary;
import swag.rest.education_platform.entity.Users;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfMaterialService {

    private final PdfMaterialRepository repository;
    private final UserService userService;
    private final TagService tagService;
    private final UserPdfLibraryRepository pdfLibraryRepository;
    @Transactional
    public void saveDocument(MultipartFile file, String username, String[] tags) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PdfMaterial pdf = new PdfMaterial();
        pdf.setTitle(file.getOriginalFilename());
        pdf.setType(file.getContentType());
        pdf.setDate(String.valueOf(LocalDate.now()));
        try {
            pdf.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(pdf.getTag() == null) pdf.setTag(new ArrayList<>());
        for (String tag : tags) {
            if (tagService.tagExist(tag)) pdf.addTag(tagService.findByTag(tag));
            else {
                Tag tempTag = new Tag();
                tempTag.setTag(tag);
                pdf.addTag(tempTag);
                tagService.saveTag(tempTag);
            }
        }

        pdf.setUser(user);
        repository.save(pdf);
    }


    @Transactional
    public void updatePdf(MultipartFile file, Long id) {
        try {
            repository.updateContent(file.getBytes(), id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional(readOnly = true)
    public PdfMaterial getDocumentById(Long id) {
        PdfMaterial pdf = repository.getonlypdfbyid(id).orElseThrow(() -> new RuntimeException("Not found"));
        return pdf;
    }

    @Transactional(readOnly = true)
    public List<PdfMaterial> getPdfs() {
       return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PdfMaterial getPdf(Long id) {
        return repository.getPdfWithoutContent(id).orElseThrow(() -> new RuntimeException("Pdf not found"));
    }

    @Transactional
    public void addToList(Long pdfId, String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        PdfMaterial pdf = repository.getPdfWithoutContent(pdfId).orElseThrow(() -> new RuntimeException("Pdf not found"));
        UserPdfLibrary libraries = user.getLibraries();
        libraries.addPdf(pdf);
    }

    @Transactional
    public void removeFromList(Long pdfId,String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        PdfMaterial pdf = repository.getPdfWithoutContent(pdfId).orElseThrow(() -> new RuntimeException("Pdf not found"));
        UserPdfLibrary library = user.getLibraries();
        library.removePdf(pdf);
    }

    @Transactional(readOnly = true)
    public List<PdfMaterial> getLibrary(String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        if(user.getLibraries().getPdf().size() == 0 ) return null;
        return user.getLibraries().getPdf();
    }

    @Transactional(readOnly = true)
    public List<PdfMaterial> getLibraryByTag(String username, String tagName) {
        List<PdfMaterial> tags = repository.findAll();
        Tag tag = tagService.findByTag(tagName);
        return tags.stream().filter((s) -> s.getTag().contains(tag)).collect(Collectors.toList());
    }


}
