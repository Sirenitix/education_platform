package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.ReflectionPostComment;
import swag.rest.education_platform.entity.SciencePostComment;

import java.util.List;

public interface ReflectionPostCommentRepository extends JpaRepository<ReflectionPostComment, Long> {

    @Query(value = "Select new ReflectionPostComment (c.id, c.content, c.user.firstname, c.user.lastname, c.user.id) from ReflectionPostComment c where c.post.id = :id")
    List<ReflectionPostComment> getOnlyCommentByPostId(Long id);
}
