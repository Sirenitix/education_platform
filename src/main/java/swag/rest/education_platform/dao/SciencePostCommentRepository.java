package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ReflectionPostComment;
import swag.rest.education_platform.entity.SciencePostComment;

import java.util.List;

public interface SciencePostCommentRepository extends JpaRepository<SciencePostComment, Long> {
    @Query(value = "Select new SciencePostComment (c.id, c.content, c.user.firstname, c.user.lastname,c.user.id) from SciencePostComment c where c.post.id = :id")
    List<SciencePostComment> getOnlyCommentByPostId(Long id);
}
