package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swag.rest.education_platform.dao.UserFullDetailsRepository;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String name;
    private String type;
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "profile")
    @JsonIgnore
    private UserFullDetails userFullDetails;

}
