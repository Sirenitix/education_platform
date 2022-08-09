package swag.rest.education_platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import swag.rest.education_platform.entity.Avatar;
import swag.rest.education_platform.entity.UserFullDetails;

@Getter
@Setter
@NoArgsConstructor
public class UserReponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    @JsonIgnore
    private Boolean enabled;
    private Avatar image;

}
