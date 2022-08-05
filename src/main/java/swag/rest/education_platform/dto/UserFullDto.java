package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserFullDto {
    private Long id;
    @Email()
    private String username;
    private String password;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    private String school;
    private String city;
    private String role;
    private String title;
}
