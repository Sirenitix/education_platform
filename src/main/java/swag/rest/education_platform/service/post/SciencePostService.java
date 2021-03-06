package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.SciencePostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.dto.SciencePostRequestDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SciencePost;
import swag.rest.education_platform.exception.PostNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SciencePostService {
    private final SciencePostRepository repository;

    public void createPost(SciencePostRequestDto dto) {
        SciencePost post = new SciencePost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        repository.save(post);
    }

    public SciencePost getPostByIdWithComment(Long id) {
        return repository.getPostByIdWithComment(id).orElseThrow(PostNotFoundException::new);
    }

    public List<SciencePost> getPosts(int page) {
        Pageable paging = PageRequest.of(page, 10);
        Page<SciencePost> pagePost = repository.findAll(paging);
        return pagePost.getContent();
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }
    public SciencePost findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PostNotFoundException());
    }
}
