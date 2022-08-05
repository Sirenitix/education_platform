package swag.rest.education_platform.entity.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class AnnotationTarget {

    private String source;

    @OneToOne
    @JsonIgnore
    private Annotation annotation;
}
