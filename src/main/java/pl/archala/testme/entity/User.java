package pl.archala.testme.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.archala.testme.component.CustomAuthorityDeserializer;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;
import pl.archala.testme.enums.RoleEnum;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> implements UserDetails {

    public static final long serialVersionUID = 7L;

    @NotBlank
    @Size(min = 3, max = 60)
    private String username;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    @Pattern(regexp = "^$|^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    private String email;

    @NotNull
    private RoleEnum role = RoleEnum.USER;

    private boolean isEnabled;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ExamAttempt> examAttempts = new ArrayList<>();

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public List<ExamAttempt> getExamAttempts() {
        return examAttempts;
    }

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", examAttempts=" + examAttempts +
                ", enabled=" + isEnabled() +
                ", id=" + getId() +
                '}';
    }
}
