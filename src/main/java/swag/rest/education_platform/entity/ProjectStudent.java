package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "project")
public class ProjectStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany
    private List<Users> users;

    @OneToMany(mappedBy = "project")
    private List<ProjectMessage> messages;
    private Long userid;
    private String userfirstname;
    private String userlastname;




    public ProjectStudent(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
