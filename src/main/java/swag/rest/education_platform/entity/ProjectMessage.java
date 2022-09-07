package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProjectMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    private String title;
    private LocalDate date;

    private String text;
    private String userfirstname;
    private String userlastname;

    String fileLink;

    String imageLink;

    @Lob
    @JsonIgnore
    private byte[] file;

    @Lob
    @JsonIgnore
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ProjectStudent project;

    public ProjectMessage(Long id, LocalDate date, String text, String userfirstname, String userlastname) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
    }
}
