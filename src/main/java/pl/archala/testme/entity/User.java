package pl.archala.testme.entity;

import lombok.*;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends AbstractEntity<Long> implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLoginDateDisplay;
    private LocalDateTime joinDate;

    @ElementCollection
    private Set<String> roles;

    @ElementCollection
    private Set<String> authorities;

    private boolean isActive;
    private boolean isNotLocked;

}
