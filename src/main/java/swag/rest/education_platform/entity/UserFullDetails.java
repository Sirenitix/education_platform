package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user_full_details")
public class UserFullDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String school;
    private String city;
    private String achievements;
    @OneToOne(mappedBy = "userFullDetails")
    private Avatar avatar;
    private String filePath;
    private String title;
    private String email;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String role;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;


}
