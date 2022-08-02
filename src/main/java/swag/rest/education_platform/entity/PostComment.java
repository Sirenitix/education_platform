package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "science_post_comment")
@Getter@Setter
@NoArgsConstructor
public class PostComment {
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
    private Post post;
    private String ownername;
    private String ownerlastname;
    private Long ownerid;

    public PostComment(Long id, String content, String ownerName, String ownerLastname, Long ownerId) {
        this.id = id;
        this.content = content;
        this.ownername = ownerName;
        this.ownerlastname = ownerLastname;
        this.ownerid = ownerId;
    }
}
