package pl.archala.testme.entity;

import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public Token() {
    }

    public Token(User user, String value, LocalDateTime expirationDate) {
        this.user = user;
        this.value = value;
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Token{" +
                "user=" + user +
                ", value='" + value + '\'' +
                ", expirationDate=" + expirationDate +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        if (!super.equals(o)) return false;
        Token token = (Token) o;
        return Objects.equals(user, token.user) && Objects.equals(value, token.value) && Objects.equals(expirationDate, token.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, value, expirationDate);
    }
}
