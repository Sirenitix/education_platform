package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.VideoMaterialRepository;
import swag.rest.education_platform.entity.VideoMaterial;
import swag.rest.education_platform.exception.VideoException;
import swag.rest.education_platform.exception.VideoNotFounException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoMaterialService {
    private final VideoMaterialRepository repository;


    public List<VideoMaterial> getVideoByTag(String tag, int page) {
        Pageable paging = PageRequest.of(page, 5);
        Page<VideoMaterial> pageVideo = repository.getVideoMaterialByTag(tag, paging);
        return pageVideo.getContent();
    }

    public void deleteVideoById(Long id, String username){
      if(!repository.findById(id).orElseThrow(VideoNotFounException::new).getUser().getUsername().equals(username)){
          throw new VideoException("You are not authorized");
      }
      repository.deleteById(id);
    }
}
