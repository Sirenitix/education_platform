package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reflexion_post")
@Getter@Setter
@NoArgsConstructor
public class ReflectionPost implements Comparable<ReflectionPost> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Lob
    private String content;
    private String title;
    private LocalDate postDate;

    @Lob
    @JsonIgnore
    private byte[] file;

    @Lob
    @JsonIgnore
    private byte[] image;

    String fileLink;

    String imageLink;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_creator_id")
    @JsonIgnore
    private Users user;

    private Long likes;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<ReflectionPostComment> comment;

    @ManyToMany
    private List<Tag> tag = new ArrayList<>();


    public void addTag(Tag tag){
        this.tag.add(tag);
    }

    public ReflectionPost(Long id) {
        this.id = id;
    }

    public ReflectionPost(Long id, String content, String title, LocalDate postDate, Long likes, List<Tag> tag) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.postDate = postDate;
        this.likes = likes;
        this.tag = tag;
    }

    @Override
    public int compareTo(ReflectionPost reflectionPost) {
        return reflectionPost.getId().compareTo(this.getId());
    }
}
