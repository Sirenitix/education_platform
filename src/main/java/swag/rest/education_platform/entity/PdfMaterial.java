package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swag.rest.education_platform.entity.annotation.Annotation;

import javax.persistence.*;

import java.sql.Date;
import java.util.*;

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

    private String date;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="uploaded_by")
    @JsonIgnore
    private Users user;
    @Lob
    @JsonIgnore
    private byte[] content;


    @OneToMany(mappedBy = "pdf")
    @JsonIgnore
    private List<Annotation> annotations;

    @ManyToMany
    @JsonIgnore
    private List<UserPdfLibrary> library;

    public PdfMaterial(byte[] content) {
        this.content = content;
    }

    public void addTag(Tag tag) {
        this.tag.add(tag);
    }

    public PdfMaterial(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PdfMaterial(Long id, String title, List<Tag> tag, String date) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfMaterial that = (PdfMaterial) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
