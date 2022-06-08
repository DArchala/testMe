package pl.archala.testme.entity;

import lombok.*;
import pl.archala.testme.entity.User;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    public static final long serialVersionUID = 8L;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @NotEmpty
    private String value;

    private LocalDateTime expirationDate;

}
