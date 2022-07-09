package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
//    id                BIGSERIAL    NOT NULL,
//    username          VARCHAR(250) NOT NULL,
//    password          VARCHAR(250) NOT NULL,
//    role              VARCHAR(250) NOT NULL,

    private String username;
    private String password;
}
