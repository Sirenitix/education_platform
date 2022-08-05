package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.UserPdfLibrary;
import swag.rest.education_platform.entity.Users;

import java.util.List;

public interface UserPdfLibraryRepository extends JpaRepository<UserPdfLibrary, Long> {

    Boolean existsByUser(Users user);
    List<UserPdfLibrary> findAllByUser(Users user);
    void deleteByUser(Users user);
}
