package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reflexion_post")
@Getter@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<ReflectionPostComment> comment;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "post2tag", joinColumns = @JoinColumn(name = "reflexion_post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public ReflectionPost(Long id) {
        this.id = id;
    }

    public ReflectionPost(Long id, String content, String title, LocalDate postDate, Long likes) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.postDate = postDate;
        this.likes = likes;
    }
}
