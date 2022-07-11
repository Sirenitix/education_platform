package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.VideoMaterialRepository;
import swag.rest.education_platform.dto.VideoUploadDto;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.entity.VideoMaterial;
import swag.rest.education_platform.exception.VideoException;
import swag.rest.education_platform.exception.VideoNotFounException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoMaterialService {
    private final VideoMaterialRepository repository;
    private final UserService userService;

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

    public void uploadVideo(VideoUploadDto dto, String username) {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        VideoMaterial video = new VideoMaterial();
        video.setTitle(dto.getTitle());
        video.setTag(dto.getTag());
        video.setUrl(dto.getUrl());
        video.setUser(user);
        repository.save(video);

    }
}
