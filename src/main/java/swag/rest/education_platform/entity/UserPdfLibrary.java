package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class UserPdfLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Users user;

    @ManyToMany
    private List<PdfMaterial> pdf;

    public void addPdf(PdfMaterial pdf) {
        if(this.pdf == null) this.pdf = new ArrayList<>();
        this.pdf.add(pdf);
    }

    public void removePdf(PdfMaterial pdf) {
        if(this.pdf == null) return;
        this.pdf.remove(pdf);
    }





}
