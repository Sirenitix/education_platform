package swag.rest.education_platform.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dto.PostResponseDto;
import swag.rest.education_platform.dao.ReflectionPostCommentRepository;
import swag.rest.education_platform.dao.ReflectionPostRepository;
import swag.rest.education_platform.dto.ReflextionPostCreateDto;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SchoolEnum;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostException;
import swag.rest.education_platform.exception.PostNotFoundException;
import swag.rest.education_platform.service.TagService;
import swag.rest.education_platform.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ReflectionPostService {

    private final ReflectionPostRepository repository;

    String baseUrl = "http://159.89.104.8:8022/reflection";
    private final TagService tagService;
    private final UserService userService;

    private final ReflectionPostCommentRepository commentRepository;

    @Transactional
    public void createPost(ReflextionPostCreateDto dto, String username) throws IOException {
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        ReflectionPost post = new ReflectionPost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPostDate(LocalDate.now());
        if(dto.getFile() != null){
            post.setFile(dto.getFile().getBytes());
        }
        if(dto.getImage() != null){
            post.setImage(dto.getImage().getBytes());
        }
        for (String tagString : dto.getTag()) {
            if (tagService.tagExist(tagString)) {
                post.addTag(tagService.findByTag(tagString));
            } else {
                post.addTag(tagService.saveTag(new Tag(tagString)));
            }
        }
        post.setUser(user);

        repository.save(post);
    }


    @Transactional(readOnly = true)
    public ReflectionPost getPostByIdWithComment(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public void updatePost(ReflextionPostCreateDto dto, Long post_id, String username) {
        Long user_id = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")).getId();
        ReflectionPost post = repository.findById(post_id).orElseThrow(PostNotFoundException::new);
        if (!post.getUser().getId().equals(user_id))
            throw new PostException("You are not owner of this post");
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        repository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts(int page) {
        Pageable paging = PageRequest.of(page, 50);
        List<ReflectionPost> pagePost = repository.findAll(paging).getContent();
        pagePost.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/refPostFile/" + post.getId());
            }
            if (post.getImage() != null) {
                post.setImageLink(baseUrl + "/refPostImage/" + post.getId());
            }
        });
        List<PostResponseDto> dto = new ArrayList<>();

        for (ReflectionPost post : pagePost) {

//            if (post.getContent().length() > 100) {
//                post.setContent(post.getContent().substring(0, 99));
//            }
            //todo the above is not synced with front
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getId());
            response.setContent(post.getContent());
            response.setTitle(post.getTitle());
            response.setUsername(post.getUser().getUsername());
            response.setTag(post.getTag());
            response.setComments(post.getComment());
            response.setLikes(post.getLikes());
            if (post.getImage() != null) {
                response.setImageLink(post.getImageLink());
            }
            if (post.getFile() != null) {
                response.setFileLink(post.getFileLink());
            }
            dto.add(response);


        }
        Collections.sort(dto);
        return dto;
    }


    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostsBySchool(int page, String school) {

        Pageable paging = PageRequest.of(page, 50);
        List<ReflectionPost> pagePost = repository.findAll(paging).getContent();
        pagePost.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/refPostFile/" + post.getId());
            }
        });
        pagePost.forEach((post) ->
        {
            if (post.getFile() != null) {
                post.setImageLink(baseUrl + "/refPostImage/" + post.getId());
            }
        });
        List<PostResponseDto> dto = new ArrayList<>();

        for (ReflectionPost post : pagePost) {
            if(SchoolEnum.valueOf(school).equals(SchoolEnum.valueOf(post.getUser().getFullDetails().getSchool()))) {
                //todo the above is not synced with front
                PostResponseDto response = new PostResponseDto();
                response.setId(post.getId());
                response.setContent(post.getContent());
                response.setTitle(post.getTitle());
                response.setUsername(post.getUser().getUsername());
                response.setTag(post.getTag());
                response.setComments(post.getComment());
                response.setLikes(post.getLikes());
                response.setImageLink(post.getImageLink());
                response.setFileLink(post.getFileLink());
                dto.add(response);
            }

        }
        Collections.sort(dto);
        return dto;
    }


    //Current User

    @Transactional(readOnly = true)
    public List<PostResponseDto> getCurrentUserPosts(int page, String username) {

        Pageable paging = PageRequest.of(page, 50);
        List<ReflectionPost> pagePost = repository.findAll(paging).getContent();
        pagePost = pagePost.stream().filter(s -> s.getUser().getUsername().equals(username)).collect(Collectors.toList());
        pagePost.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/refPostFile/" + post.getId());
            }
        });
        pagePost.forEach((post) ->
        {
            if (post.getFile() != null) {
                post.setImageLink(baseUrl + "/refPostImage/" + post.getId());
            }
        });
        List<PostResponseDto> dto = new ArrayList<>();

        for (ReflectionPost post : pagePost) {

            if (post.getContent().length() > 100) {
                post.setContent(post.getContent().substring(0, 99));
            }
            PostResponseDto response = new PostResponseDto();
            response.setId(post.getId());
            response.setContent(post.getContent());
            response.setTitle(post.getTitle());
            response.setUsername(post.getUser().getUsername());
            response.setTag(post.getTag());
            response.setImageLink(post.getImageLink());
            response.setFileLink(post.getFileLink());
            dto.add(response);


        }
        Collections.sort(dto);
        return dto;
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public ReflectionPost findById(Long id) {

        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public Page<ReflectionPost> findByTag(Integer offset, Integer limit, Tag tag) {
        Pageable paging = PageRequest.of(offset, limit);

        return repository.findByTag(tag, paging);
    }

    @Transactional(readOnly = true)
    public Set<ReflectionPost> searchPost(String title, String content, String tag) {

        Set<ReflectionPost> result;// = repository.findAllByTitleContaining(title);
        if(title.isEmpty() && content.isEmpty() && tag.isEmpty())
        {
            List<ReflectionPost> reflectionPosts = repository.findAll();
            Collections.sort(reflectionPosts);
            reflectionPosts.forEach((post) -> {
                if (post.getFile() != null) {
                    post.setFileLink(baseUrl + "/refPostFile/" + post.getId());
                }
                if (post.getFile() != null) {
                    post.setImageLink(baseUrl + "/refPostImage/" + post.getId());
                }
            });
            return new HashSet<>(reflectionPosts);
        }

        result = new HashSet<>(repository.findAll());
        if(!title.isEmpty())  {
            result = result.stream().filter(u -> u.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toSet());
        }
        if(!content.isEmpty()) {
            result = result.stream().filter(u -> u.getContent().equalsIgnoreCase(content)).collect(Collectors.toSet());
        }
        if(!tag.isEmpty()) {
            result = result.stream().filter(u -> u.getTag() != null)
                    .filter(u -> u.getTag().get(0).getTag().equalsIgnoreCase(tag)).collect(Collectors.toSet());
        }

        result.forEach((post) -> {
            if (post.getFile() != null) {
                post.setFileLink(baseUrl + "/refPostFile/" + post.getId());
            }
            if (post.getFile() != null) {
                post.setImageLink(baseUrl + "/refPostImage/" + post.getId());
            }
        });

        return result;
    }
}
