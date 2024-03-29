package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProjectStudent implements Comparable<ProjectStudent>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany
    private List<Users> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectMessage> messages;
    private Long userid;
    private String userfirstname;
    private String userlastname;



    private String description;


    public ProjectStudent(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public int compareTo(ProjectStudent projectStudent) {
        return projectStudent.getId().compareTo(this.getId());
    }
}
