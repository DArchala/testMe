package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UserAuthServiceTest {

    @InjectMocks
    private UserAuthService userAuthService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private TokenRepository tokenRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @Test
    void activateAccountByTokenShouldThrowExceptionIfTokenDoesNotExist() {
        //when
        when(tokenRepo.findByValue("value")).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.activateAccountByToken("value"));
    }

    @Test
    void activateAccountByTokenShouldThrowExceptionIfTokenHasExpired() {
        //given
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().minusMinutes(60));

        //when
        when(tokenRepo.findByValue("value")).thenReturn(Optional.of(token));

        //then
        assertThrows(RuntimeException.class, () -> userAuthService.activateAccountByToken("value"));
    }

    @Test
    void activateAccountByTokenShouldThrowExceptionIfTokenUserDoesNotExist() {
        //given
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));

        //when
        when(tokenRepo.findByValue("value")).thenReturn(Optional.of(token));

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.activateAccountByToken("value"));
    }

    @Test
    void activateAccountByTokenShouldNotThrowAnyException() {
        //given
        Token token = new Token();
        token.setUser(new User());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));

        //when
        when(tokenRepo.findByValue("value")).thenReturn(Optional.of(token));

        //then
        assertDoesNotThrow(() -> userAuthService.activateAccountByToken("value"));
    }

    @Test
    void registerUserShouldThrowExceptionIfUsernameIsAlreadyTaken() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        //then
        assertThrows(EntityExistsException.class, () -> userAuthService.registerUser(user));
    }

    @Test
    void registerUserShouldThrowExceptionIfEmailIsAlreadyTaken() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //then
        assertThrows(EntityExistsException.class, () -> userAuthService.registerUser(user));
    }

    @Test
    void registerUserShouldThrowExceptionIfUsernameIsEqualToEmail() {
        //given
        User user = new User("user@gmail.com", "password", "user@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> userAuthService.registerUser(user));
    }

    @Test
    void registerUserShouldThrowExceptionIfUsernameIsEqualToPassword() {
        //given
        User user = new User("password", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> userAuthService.registerUser(user));
    }

    @Test
    void registerUserShouldThrowExceptionIfEmailIsEqualToPassword() {
        //given
        User user = new User("username", "email@gmail.com", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> userAuthService.registerUser(user));
    }

    @Test
    void registerUserShouldNotThrowAnyExceptionIfUserDataAreCorrect() {
        //given
        User user = new User("username", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertDoesNotThrow(() -> userAuthService.registerUser(user));
    }

    @Test
    void resetPasswordShouldThrowExceptionIfUserDoesNotExist() {
        //given
        String email = "email-123@gmail.com";

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.resetPassword(email));
    }

    @Test
    void resetPasswordShouldThrowExceptionIfUserIsDisabled() {
        //given
        String email = "email-123@gmail.com";
        User user = new User();

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        //then
        assertThrows(IllegalArgumentException.class, () -> userAuthService.resetPassword(email));
    }

    @Test
    void resetPasswordShouldSendTokenMail() {
        //given
        String email = "email-123@gmail.com";
        User user = new User();
        user.setEnabled(true);

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        //then
        assertDoesNotThrow(() -> userAuthService.resetPassword(email));
    }

    @Test
    void activateAccountByTokenShouldDeleteUserIfTokenHasExpiredAndUserExistAndUserIsDisabled() {
        //given
        User user = new User();
        user.setEnabled(false);
        user.setUsername("username");

        Token token = new Token();
        token.setUser(user);
        token.setExpirationDate(LocalDateTime.now().minusMinutes(60));

        //when
        when(tokenRepo.findByValue("value")).thenReturn(Optional.of(token));
        when(userRepo.findByUsername(token.getUser().getUsername())).thenReturn(Optional.of(user));

        //then
        assertThrows(RuntimeException.class, () -> userAuthService.activateAccountByToken("value"));
    }

    @Test
    void resetPasswordByTokenShouldThrowExceptionIfUserDoesNotExist() {
        //given
        String value = "valueTOKEN";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.resetPasswordByToken(value));
    }

    @Test
    void resetPasswordByTokenShouldThrowExceptionIfTokenHasExpired() {
        //given
        String value = "valueTOKEN";
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().minusMinutes(60));

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertThrows(RuntimeException.class, () -> userAuthService.resetPasswordByToken(value));
    }

    @Test
    void resetPasswordByTokenShouldNotThrowAnyException() {
        //given
        String value = "valueTOKEN";
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertDoesNotThrow(() -> userAuthService.resetPasswordByToken(value));
    }

    @Test
    void findUserByTokenValueShouldThrowExceptionIfTokenDoesNotExist() {
        //given
        String value = "tokenValue123";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.findUserByTokenValue(value));
    }

    @Test
    void findUserByTokenValueShouldThrowExceptionIfTokenUserDoesNotExist() {
        //given
        Token token = new Token();
        String value = "tokenValue123";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertThrows(EntityNotFoundException.class, () -> userAuthService.findUserByTokenValue(value));
    }

    @Test
    void findUserByTokenValueShouldReturnUser() {
        //given

        Token token = new Token();
        User user = new User();
        token.setUser(user);
        String value = "tokenValue123";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertEquals(user, userAuthService.findUserByTokenValue(value));
    }
}