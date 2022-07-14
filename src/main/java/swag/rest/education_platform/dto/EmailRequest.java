package swag.rest.education_platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swag.rest.education_platform.email_client.entity.Email;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmailRequest {
    Email emails;
}
