package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.VideoMaterial;

import java.util.List;

public interface VideoMaterialRepository extends JpaRepository<VideoMaterial, Long> {

    Page<VideoMaterial> getVideoMaterialByTag(String tag, Pageable page);

}
