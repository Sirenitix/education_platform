package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reflexion_post_comment")
@Getter@Setter
public class ReflectionPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private Users user;

    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ReflexionPost post;
}
