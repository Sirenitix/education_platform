package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "science_post")
@Getter@Setter
@NoArgsConstructor
public class SciencePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String content;
    private String title;
    private LocalDate postDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    private Long likes;

    @OneToMany(mappedBy = "post")
    private List<SciencePostComment> comment;
    public SciencePost(Long id, String content, String title, LocalDate postDate, Long likes) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.postDate = postDate;
        this.likes = likes;
    }
}
