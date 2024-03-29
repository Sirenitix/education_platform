package swag.rest.education_platform.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import swag.rest.education_platform.entity.annotation.Annotation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    private String role;

    public Users(Long id, String username, String password, boolean enabled, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
    private String firstname;
    private String lastname;
    //relations with other entities
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ReflectionPost> reflectionPosts;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PostComment> postComments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Annotation> annotations;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private UserPdfLibrary libraries;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ReflectionPostComment> reflectionPostComments;


    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private UserFullDetails fullDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<VideoMaterial> videos;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PdfMaterial> pdfs;

    @JsonIgnore
    @ManyToMany
    private List<ProjectStudent> projects;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<ProjectMessage> messages;
    //--------------------------------


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Users(Long id, String username, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
