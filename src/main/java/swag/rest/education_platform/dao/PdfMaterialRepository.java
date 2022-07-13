package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.PdfMaterial;

import java.util.List;
import java.util.Optional;

public interface PdfMaterialRepository extends JpaRepository<PdfMaterial, Long> {
    @Query("select new PdfMaterial(p.content) from PdfMaterial p where p.id = :id")
    Optional<PdfMaterial> getonlypdfbyid(Long id);
}