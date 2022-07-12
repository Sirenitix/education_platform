package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "science_post_comment")
@Getter@Setter
@NoArgsConstructor
public class SciencePostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private Users user;

    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SciencePost post;
    private String ownername;
    private String ownerlastname;
    private Long ownerid;

    public SciencePostComment(Long id, String content, String ownername, String ownerlastname, Long ownerid) {
        this.id = id;
        this.content = content;
        this.ownername = ownername;
        this.ownerlastname = ownerlastname;
        this.ownerid = ownerid;
    }
}
