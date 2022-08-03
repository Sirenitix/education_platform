package swag.rest.education_platform.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swag.rest.education_platform.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p left join fetch p.comment where p.id = :id")
    Optional<Post> getPostByIdWithComment(Long id);
    @Override
    Page<Post> findAll(Pageable page);


    Optional<Post> findById(Long id);
    Set<Post> findAllByContentContaining(String content);
    Set<Post> findAllByTitleContaining(String title);
}
