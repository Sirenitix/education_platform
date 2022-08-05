package swag.rest.education_platform.entity.annotation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class AnnotationSelector {

    private AnnotationNode node;
    private String opacity;
    private String subtype;
    private List<String> boundingBox;
    private List<String> quadPoints;
    private String strokeColor;
    private Integer strokeWidth;
    private String type;





}
