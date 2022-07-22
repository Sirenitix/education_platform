package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.TagRepository;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Tag;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Set<ProjectStudent> getProjectByTagId(Integer offset, Integer limit, Integer id) {
        Pageable nextPage = PageRequest.of(offset, limit);
        String name = Objects.requireNonNull(tagRepository.findById(id).orElse(null)).getName();
        return tagRepository.findByName(name).getPosts();
    }

    public Page<Tag> getAllProject(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        Page<Tag> tags = Objects.requireNonNull(tagRepository.findAll(nextPage));
        return tags;
    }

}
