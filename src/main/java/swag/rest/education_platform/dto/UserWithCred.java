package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithCred {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Boolean enabled;

    private String password;

    public UserWithCred(Long id, String username, String firstname, String lastname, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enabled = enabled;
    }


}
