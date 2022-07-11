package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ProjectStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany
    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "project_student_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<Users> users;

    @OneToMany(mappedBy = "project")
    private List<ProjectMessage> messages;
}
