package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.TagRepository;
import swag.rest.education_platform.entity.Tag;

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



    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
