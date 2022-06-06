package pl.archala.testme.security;

import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void hashCodeShouldBeEqualIfTokensAreSame() {
        //given
        User user = new User();
        LocalDateTime ldt = LocalDateTime.now();
        Token t1 = new Token(user, "value", ldt);
        Token t2 = new Token(user, "value", ldt);

        //then
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void hashCodeShouldBeNotEqualIfTokensAreDifferent() {
        //given
        LocalDateTime ldt = LocalDateTime.now();
        Token t1 = new Token(new User(), "value1", ldt);
        Token t2 = new Token(new User(), "value2", ldt);

        //then
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void tokensShouldBeEqualIfHaveSameFields() {
        //given
        User user = new User();
        LocalDateTime ldt = LocalDateTime.now();
        Token t1 = new Token(user, "value", ldt);
        Token t2 = new Token(user, "value", ldt);

        //then
        assertEquals(t1, t2);
        assertEquals(t2, t1);
    }

    @Test
    void tokensShouldBeNotEqualIfHaveDifferentFields() {
        //given
        User user = new User();
        LocalDateTime ldt = LocalDateTime.now();
        Token t1 = new Token(user, "value1", ldt);
        Token t2 = new Token(new User(), "value2", ldt);

        //then
        assertNotEquals(t1, t2);
        assertNotEquals(t2, t1);
    }

}