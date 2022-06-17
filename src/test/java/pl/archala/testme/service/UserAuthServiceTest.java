package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
}