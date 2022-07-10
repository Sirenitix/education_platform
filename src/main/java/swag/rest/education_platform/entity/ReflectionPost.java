package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reflexion_post")
@Getter@Setter
public class ReflectionPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String content;
    private String title;
    private LocalDate postDate;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private Users user;

    private Long likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<ReflectionPostComment> comment;

}
