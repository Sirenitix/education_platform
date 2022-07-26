package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.TagRepository;
import swag.rest.education_platform.entity.Tag;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public Page<Tag> getAllProject(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        Page<Tag> tags = Objects.requireNonNull(tagRepository.findAll(nextPage));
        return tags;
    }


    public Tag getProject(String name) {
        return Objects.requireNonNull(tagRepository.findByName(name));
    }

}
