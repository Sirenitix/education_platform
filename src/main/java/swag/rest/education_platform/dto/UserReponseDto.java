package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class UserReponseDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Boolean enabled;

    public UserReponseDto(Long id, String username, String firstname, String lastname, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enabled = enabled;
    }


}
