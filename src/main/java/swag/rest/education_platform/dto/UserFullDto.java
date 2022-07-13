package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFullDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String school;
    private String city;
}
