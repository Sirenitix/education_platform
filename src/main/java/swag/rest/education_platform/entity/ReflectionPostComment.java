package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reflexion_post_comment")
@Getter@Setter
@NoArgsConstructor
public class ReflectionPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    @JsonIgnore
    private Users user;

    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private ReflectionPost post;
    private String firstname;
    private String lastname;
    private Long ownerid;

    public ReflectionPostComment(Long id, String content, String firstname, String lastname, Long ownerid) {
        this.id = id;
        this.content = content;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ownerid = ownerid;
    }
}
