package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.ReflextionPostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.exception.PostNotFoundException;

import java.util.List;

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
    public ReflectionPost getPostByIdWithComment(Long id) {
        return repository.getPostByIdWithComment(id).orElseThrow(() -> new PostNotFoundException());
    }

    public List<ReflectionPost> getPosts(int page) {
        Pageable paging = PageRequest.of(page, 10);
        Page<ReflectionPost> pagePost = repository.findAll(paging);
        return pagePost.getContent();
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
