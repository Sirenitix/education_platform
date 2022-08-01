package swag.rest.education_platform.dao;

import com.groupdocs.conversion.internal.c.a.w.internal.Ta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAll();

    void saveVoid(Tag tag);

    Optional<Tag> findByTag(String tag);

    Boolean existsByTag(String tag);

}