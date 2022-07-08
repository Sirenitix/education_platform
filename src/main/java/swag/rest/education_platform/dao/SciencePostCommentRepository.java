package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swag.rest.education_platform.entity.SciencePostComment;

public interface SciencePostCommentRepository extends JpaRepository<SciencePostComment, Long> {
}
