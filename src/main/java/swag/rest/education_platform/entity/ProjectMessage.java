package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ProjectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private LocalDate date;
    private String text;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectStudent project;
}
