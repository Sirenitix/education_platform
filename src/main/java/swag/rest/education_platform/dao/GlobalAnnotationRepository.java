package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.entity.annotation.Annotation;

import java.util.List;

public interface GlobalAnnotationRepository extends JpaRepository<Annotation, Long> {

//    @Query(value = "Select * from annotation as t where t.pdf_id = :pdf", nativeQuery = true)
    List<Annotation> findAllByPdf(PdfMaterial pdf);

    List<Annotation> findAllByPdfAndUser(PdfMaterial pdf, Users user);



}
