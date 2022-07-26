package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Users user;

    public VideoMaterial(Long id, String title, String tag, String url) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.url = url;
    }
}
