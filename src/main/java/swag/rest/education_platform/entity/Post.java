package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "science_post")
@Getter@Setter
@NoArgsConstructor
public class Post  implements Comparable<Post>{
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

    private String fileLink;

    private String imageLink;


    @Lob
    @JsonIgnore
    private byte[] file;

    @Lob
    @JsonIgnore
    private byte[] image;


    private Long likes;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comment;
    public Post(Long id, String content, String title, LocalDate postDate, Long likes) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.postDate = postDate;
        this.likes = likes;
    }

    @Override
    public int compareTo(Post post) {
        return post.getId().compareTo(this.getId());
    }



}
