package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PdfMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany
    private List<Tag> tag;

    private LocalDate date;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="uploaded_by")
    @JsonIgnore
    private Users user;
    @Lob
    @JsonIgnore
    private byte[] content;

    public PdfMaterial(byte[] content) {
        this.content = content;
    }

    public void addTag(Tag tag) {
        this.tag.add(tag);
    }

    public PdfMaterial(Long id, String title, List<Tag> tag) {
        this.id = id;
        this.title = title;
        this.tag = tag;
    }
}
