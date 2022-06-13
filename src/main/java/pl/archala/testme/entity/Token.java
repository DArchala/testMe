package pl.archala.testme.entity;

import lombok.*;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @OneToOne
    private User user;

    @NotEmpty
    @Size(min = 10)
    private String value;

    private LocalDateTime expirationDate;

}
