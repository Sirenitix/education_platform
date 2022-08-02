package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.TagRepository;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.exception.TagNotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag getProject(String name) {
        return Objects.requireNonNull(tagRepository.findByTag(name)).orElseThrow(TagNotFoundException::new);
    }

    public Page<Tag> getAllProject(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        Page<Tag> tags = Objects.requireNonNull(tagRepository.findAll(nextPage));
        return tags;
    }


    public boolean tagExist(String tag) {
        return tagRepository.existsByTag(tag);
    }

    public Tag findByTag(String tag) {
        return tagRepository.findByTag(tag).orElseThrow(()-> new RuntimeException("Tag not found"));
    }



    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
