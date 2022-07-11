package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VideoMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String tag;
    private String url;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private Users user;

}
