package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.ReflextionPostRepository;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ReflextionPostService {

    private final ReflextionPostRepository repository;
}
