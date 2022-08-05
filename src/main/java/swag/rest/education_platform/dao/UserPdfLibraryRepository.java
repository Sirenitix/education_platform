package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.UserPdfLibrary;
import swag.rest.education_platform.entity.Users;

public interface UserPdfLibraryRepository extends JpaRepository<UserPdfLibrary, Long> {

    Boolean existsByPdfAndUser(PdfMaterial pdf, Users user);
}
