package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swag.rest.education_platform.entity.Avatar;
import swag.rest.education_platform.entity.UserFullDetails;

@Getter
@Setter
@NoArgsConstructor
public class CurrentUserDto {
    private UserFullDetails userFullDetails;

}
