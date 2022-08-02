package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.entity.ProjectStudent;

import java.util.List;

@Getter
@Setter
public class UserFullDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String school;
    private String city;
    private String role;
}
