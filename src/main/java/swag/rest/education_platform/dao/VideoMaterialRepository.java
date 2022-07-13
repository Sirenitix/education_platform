package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.VideoMaterial;

import java.util.List;

public interface VideoMaterialRepository extends JpaRepository<VideoMaterial, Long> {

    Page<VideoMaterial> getVideoMaterialByTag(String tag, Pageable page);

    @Query("select new VideoMaterial(v.id, v.title, v.tag, v.url) from VideoMaterial v")
    List<VideoMaterial> findAllVideos();
}
