package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.ReflectionPost;

import java.util.Optional;

public interface ReflectionPostRepository extends JpaRepository<ReflectionPost,Long> {


    @Query("select p from ReflectionPost p left join fetch p.comment where p.id = :id")
    Optional<ReflectionPost> getPostByIdWithComment(Long id);
//    Page<ReflectionPost> findAll(Pageable page);

}
