package pl.archala.testme.entity;

import org.junit.jupiter.api.Test;
import pl.archala.testme.enums.RoleEnum;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getAuthoritiesShouldReturnUserRoleAsSimpleGrantedAuthority() {
        //given
        User user = new User();
        user.setRole(RoleEnum.USER);

        //when
        String submit = user.getAuthorities().stream().findFirst().get().toString();

        //then
        assertEquals("USER", submit);
    }

    @Test
    void hashCodeShouldBeEqualForTheSameUsers() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);
        User user2 = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        //when
        int hash = user.hashCode();
        int hash2 = user2.hashCode();

        //then
        assertEquals(hash, hash2);
    }

    @Test
    void hashCodeShouldNotBeEqualForDifferentUsers() {
        //given
        User user = new User("user2", "password", "email@gmail.com", RoleEnum.USER, true);
        User user2 = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        //when
        int hash = user.hashCode();
        int hash2 = user2.hashCode();

        //then
        assertNotEquals(hash, hash2);
    }

    @Test
    void usersShouldNotBeEqualIfFieldsAreDifferrent() {

        User user = new User("user2", "password", "email@gmail.com", RoleEnum.USER, true);
        User user2 = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        assertFalse(user.equals(user2));
        assertFalse(user2.equals(user));

    }

    @Test
    void usersShouldNotEqualIfFieldsAreTheSame() {

        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);
        User user2 = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        assertTrue(user.equals(user2));
        assertTrue(user2.equals(user));

    }

}