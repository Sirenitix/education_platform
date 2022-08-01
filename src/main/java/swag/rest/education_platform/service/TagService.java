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




    public boolean tagExist(String tag) {
        return tagRepository.existsByTag(tag);
    }

    public Tag findByTag(String tag) {
        return tagRepository.findByTag(tag).orElseThrow(()-> new RuntimeException("Tag not found"));
    }



    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }
}
