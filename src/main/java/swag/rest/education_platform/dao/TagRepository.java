package swag.rest.education_platform.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @EntityGraph(value = "tag.posts", type = EntityGraph.EntityGraphType.FETCH)
    List<Tag> findAll();

    List<Tag> findByPostsIn(Set<ProjectStudent> projectStudent);

    Tag findByName(String name);
}