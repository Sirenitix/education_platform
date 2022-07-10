package swag.rest.education_platform.entity;

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
    private Long id;
    private String name;
    private String type;
    private byte[] picByte;

    @OneToOne
    @JoinColumn(name = "profile")
    private UserFullDetails userFullDetails;

}
