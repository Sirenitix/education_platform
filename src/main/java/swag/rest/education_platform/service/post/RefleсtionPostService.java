package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.ReflextionPostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class RefleсtionPostService {

    private final ReflextionPostRepository repository;
    @Transactional
    public void createPost(ReflextionPostCreateDto dto) {
        ReflectionPost post = new ReflectionPost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        repository.save(post);
    }
    public ReflectionPost findPostById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException());

    }
}
