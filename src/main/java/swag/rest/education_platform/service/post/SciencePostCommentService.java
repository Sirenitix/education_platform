package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.SciencePostCommentRepository;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SciencePostCommentService {
    private final SciencePostCommentRepository repository;
}
