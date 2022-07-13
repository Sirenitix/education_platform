package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swag.rest.education_platform.entity.ProjectMessagesDto;
import swag.rest.education_platform.entity.ProjectStudent;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectStudentDto  {

    private Long id;
    private String title;
    private List<UserFullDto> users;
    private List<ProjectMessagesDto> messages;

    public ProjectStudentDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
