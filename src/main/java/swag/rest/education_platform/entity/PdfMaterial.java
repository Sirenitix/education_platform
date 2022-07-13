package swag.rest.education_platform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PdfMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String tag;
    private LocalDate date;
    private String type;
    @ManyToOne
    @JoinColumn(name ="uploaded_by")
    private Users user;
    @Lob
    private byte[] content;

    public PdfMaterial(byte[] content) {
        this.content = content;
    }
}
