package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.SciencePost;

public interface SciencePostRepository extends JpaRepository<SciencePost,Long> {
}
