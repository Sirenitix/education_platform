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

    public UserReponseDto(Long id, String username, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
