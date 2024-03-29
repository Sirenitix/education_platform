package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReflectionPostRepository extends JpaRepository<ReflectionPost,Long> {


    @Query("select p from ReflectionPost p left join fetch p.comment where p.id = :id")
    Optional<ReflectionPost> getPostByIdWithComment(Long id);
//    Page<ReflectionPost> findAll(Pageable page);


    Optional<ReflectionPost> findById(Long id);


    Page<ReflectionPost> findByTag(Tag tag, Pageable pageable);

    Set<ReflectionPost> findAllByTitleContaining(@Param("title") String title);

    @Query("select p from ReflectionPost p where p.content like %:content%")
    Set<ReflectionPost> customFindAllByContentContaining(@Param("content")String content);
}
