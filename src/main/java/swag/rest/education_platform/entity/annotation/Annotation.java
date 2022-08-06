package swag.rest.education_platform.entity.annotation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import swag.rest.education_platform.entity.PdfMaterial;
import swag.rest.education_platform.entity.Users;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Annotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long annotationId;
    @Column(columnDefinition = "varchar (1000)")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_id")
    @JsonIgnore
    private PdfMaterial pdf;

//    private String type;
//    private String id;
//    private String bodyValue;
//    private String motivation;
//    @OneToOne
//    private AnnotationTarget target;
//    @OneToOne
//    private AnnotationCreator creator;
//    private String created;
//    private String modified;
}
