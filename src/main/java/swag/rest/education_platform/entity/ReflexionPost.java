package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reflexion_post")
@Getter@Setter
public class ReflexionPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private Users user;

    private Long likes;

    @OneToMany(mappedBy = "post")
    private List<ReflectionPostComment> comment;

}
