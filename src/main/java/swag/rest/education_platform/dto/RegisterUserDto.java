package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String school;
    private String city;
    private String title;

}
