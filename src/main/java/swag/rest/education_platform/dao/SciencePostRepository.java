package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;

import java.util.Optional;

public interface SciencePostRepository extends JpaRepository<SciencePost,Long> {
    @Query("select p from SciencePost p left join fetch p.comment where p.id = :id")
    Optional<SciencePost> getPostByIdWithComment(Long id);
    @Override
    Page<SciencePost> findAll(Pageable page);

    @Query("Select new SciencePost (p.id, p.content,p.title, p.postDate,  p.likes) from SciencePost p where p.id = :id")
    SciencePost getOnlyPostById(Long id);

}
