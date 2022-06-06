package pl.archala.testme.security;

import lombok.*;
import pl.archala.testme.entity.User;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "tokens")
public class Token extends AbstractEntity<Long> {

    @OneToOne
    private User user;

    private String value;

    private LocalDateTime expirationDate;

}
