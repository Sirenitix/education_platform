package swag.rest.education_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_tag_id", sequenceName = "seq_tag_id", initialValue = 1001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tag_id")
    @JsonIgnore
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReflectionPost> posts = new HashSet<>();



    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", posts='" + posts + '\'' +
                '}';
    }
}