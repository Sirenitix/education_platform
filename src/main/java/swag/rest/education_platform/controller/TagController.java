package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ReflectionPost;
import swag.rest.education_platform.entity.SchoolEnum;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.service.TagService;
import swag.rest.education_platform.service.post.ReflectionPostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService tagService;

    private final ReflectionPostService refleсtionPostService;

    @PostMapping("/post_by_tag")
    @Transactional
    public List<ReflectionPost> getTagPage(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit,
                                           @RequestBody Tag tag) {
        Tag tagCurrent = tagService.getProject(tag.getTag());
        Page<ReflectionPost> posts = refleсtionPostService.findByTag(offset, limit, tagCurrent);

        return posts.toList();
    }

    @GetMapping("/tags")
    public List<Tag> getPostByTag(@RequestParam("offset") Integer offset,
                                  @RequestParam("limit") Integer limit) {
        Page<Tag> projectStudents = tagService.getAllProject(offset, limit);
        return projectStudents.toList();
    }

    @GetMapping("/schoolTags")
    public List<String> getSchoolTags() {
        List<String> tags = new ArrayList<>();
         Arrays.asList(SchoolEnum.values()).forEach(s -> tags.add(s.name()));
         return tags;
    }

}
