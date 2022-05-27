package pl.archala.testme.security;

import pl.archala.testme.entity.User;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token extends AbstractEntity<Long> {

    private String value;

    @OneToOne
    private User user;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
