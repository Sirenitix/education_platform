package swag.rest.education_platform.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String password;

    private boolean enabled;

    private String role;

    public Users(Long id, String username, String password, boolean enabled, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

//relations with other entities

    @OneToMany(mappedBy = "user")
    private List<SciencePost> sciencePosts;

    @OneToMany(mappedBy = "user")
    private List<ReflectionPost> reflectionPosts;

    @OneToMany(mappedBy = "user")
    private List<SciencePostComment> sciencePostComments;

    @OneToMany(mappedBy = "user")
    private List<ReflectionPostComment> reflectionPostComments;

    @OneToOne(mappedBy = "user")
    private UserFullDetails fullDetails;

    @OneToMany(mappedBy = "user")
    private List<VideoMaterial> videos;

    @OneToMany(mappedBy = "user")
    private List<PdfMaterial> pdfs;

    //--------------------------------


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
