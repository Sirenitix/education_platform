package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Tag;
import swag.rest.education_platform.service.TagService;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService tagService;

    @GetMapping("/tags")
    public List<Tag> getTagPage(@RequestParam("offset") Integer offset,
                                @RequestParam("limit") Integer limit) {
        Page<Tag> projectStudents = tagService.getAllProject(offset, limit);
        return projectStudents.toList();
    }


}
