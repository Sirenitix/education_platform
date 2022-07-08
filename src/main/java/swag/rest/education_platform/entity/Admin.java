package swag.rest.education_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Admin {
    String username;
    Boolean isMaster;
}
