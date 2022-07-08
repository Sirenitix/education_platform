package swag.rest.bank_app_delivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "science_post")
@Getter@Setter
public class SciencePost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Long likes;

    @OneToMany(mappedBy = "post")
    private List<SciencePostComment> comment;

}
