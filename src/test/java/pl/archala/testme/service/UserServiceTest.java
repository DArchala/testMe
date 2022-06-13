package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pl.archala.testme.enums.RoleEnum.ADMIN;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepo;

    @Mock
    private MailService mailService;

    @Test
    void registerUserShouldReturnOneIfUserFoundByUsername() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        //then
        assertEquals(1, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnTwoIfUserFoundByEmail() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //then
        assertEquals(2, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnZeroIfUserWasSavedAndTokenWasSent() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.registerUser(user));
    }

    @Test
    void deleteUserShouldReturnZeroIfUserCannotBeFoundById() {
        //when
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.deleteUser(anyLong()));
    }

    @Test
    void deleteUserShouldReturnOneIfAdminWantToDeleteTheLastAdminInDB() {
        //given
        User user = new User();
        user.setRole(RoleEnum.ADMIN);
        List<User> admins = new ArrayList<>(List.of(user));

        //when
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.findAll().stream().filter(u -> u.getRole().equals(RoleEnum.ADMIN)).collect(Collectors.toList()))
                .thenReturn(admins);

        //then
        assertEquals(1, userService.deleteUser(anyLong()));
    }

    @Test
    void deleteUserShouldReturnTwoIfAllConditionsAreMet() {
        //given
        User user = new User();
        User user2 = new User();
        user.setRole(RoleEnum.ADMIN);
        user2.setRole(RoleEnum.ADMIN);
        List<User> admins = new ArrayList<>(List.of(user, user2));

        //when
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.findAll().stream().filter(u -> u.getRole().equals(RoleEnum.ADMIN)).collect(Collectors.toList()))
                .thenReturn(admins);

        //then
        assertEquals(2, userService.deleteUser(anyLong()));
    }

    @Test
    void updateUserRoleShouldReturnZeroIfUserIsNew() {
        //given
        User user = new User();

        //then
        assertEquals(0, userService.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnZeroIfUserCannotBeFoundById() {
        //given
        User user = new User();
        user.setId(100L);

        //when
        when(userRepo.findById(100L)).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnOneIfUserWasSaved() {
        //given
        User user = new User();
        user.setId(100L);

        //when
        when(userRepo.findById(100L)).thenReturn(Optional.of(user));

        //then
        assertEquals(1, userService.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnTwoIfThereIsARequestToSetLastAdminAsUser() {
        //given
        User newUser = new User();
        newUser.setId(100L);

        User currentuser = new User();
        currentuser.setId(100L);
        currentuser.setRole(ADMIN);

        List<User> admins = new ArrayList<>(List.of(currentuser));

        //when
        when(userRepo.findById(100L)).thenReturn(Optional.of(currentuser));
        when(userRepo.findByRole(ADMIN)).thenReturn(admins);

        //then
        assertEquals(2, userService.updateUserRole(newUser));
    }

    @Test
    void activateAccountShouldReturnZeroIfTokenDoesNotExist() {
        //given
        String tokenValue = "sample-token-value-123";

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountShouldReturnOneIfTokenHasExpired() {
        //given
        User user = new User();
        user.setUsername("username");

        Token token = new Token();
        token.setUser(user);
        token.setExpirationDate(LocalDateTime.now().minusMinutes(10));
        String tokenValue = "sample-token-value-123";

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.of(token));
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));

        //then
        assertEquals(1, userService.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountShouldReturnTwoIfTokenHasNoUser() {
        //given
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));
        String tokenValue = "sample-token-value-123";

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.of(token));

        //then
        assertEquals(2, userService.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountShouldReturnThreeIfUserWasSavedAndTokenWasDeleted() {
        //given
        Token token = new Token();
        token.setUser(new User());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));
        String tokenValue = "sample-token-value-123";

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.of(token));

        //then
        assertEquals(3, userService.activateAccountByToken(tokenValue));
    }

    @Test
    void updatePasswordShouldReturnZeroIfUserDoesNotExist() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setUsername("username");

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.updatePasswordByRequest(passwordChangeRequest));
    }

    @Test
    void updatePasswordShouldReturnOneIfPasswordFromRequestAndUserPasswordFromDBDoNotMatch() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setUsername("username");
        User user = new User();

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())).thenReturn(false);

        //then
        assertEquals(1, userService.updatePasswordByRequest(passwordChangeRequest));
    }

    @Test
    void updatePasswordShouldReturnTwoIfEmailDoNotMatchToUsername() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setUsername("username");
        passwordChangeRequest.setEmail("email2");

        User user = new User();
        user.setEmail("email1");

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())).thenReturn(true);

        //then
        assertEquals(2, userService.updatePasswordByRequest(passwordChangeRequest));
    }

    @Test
    void updatePasswordShouldReturnThreeIfNewPasswordIsEqualToCurrentPassword() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setUsername("username");
        passwordChangeRequest.setEmail("email1");

        User user = new User();
        user.setEmail("email1");

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(passwordChangeRequest.getNewPassword(), user.getPassword())).thenReturn(true);

        //then
        assertEquals(3, userService.updatePasswordByRequest(passwordChangeRequest));
    }

    @Test
    void updatePasswordShouldReturnFourIfAllConditionsAreMet() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setUsername("username");
        passwordChangeRequest.setNewPassword("newPass");
        passwordChangeRequest.setEmail("email1");

        User user = new User();
        user.setPassword("currentPass");
        user.setEmail("email1");

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(passwordChangeRequest.getNewPassword(), user.getPassword())).thenReturn(false);

        //then
        assertEquals(4, userService.updatePasswordByRequest(passwordChangeRequest));
    }

    @Test
    void resetPasswordShouldReturnZeroIfUserDoesNotExist() {
        //given
        String email = "email@gmail.com";

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnTwoIfUserIsDisabled() {
        //given
        String email = "email@gmail.com";
        User user = new User();

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        //then
        assertEquals(2, userService.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnOneIfPasswordResetTokenWasSent() {
        //given
        String email = "email@gmail.com";
        User user = new User();
        user.setEnabled(true);

        //when
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        //then
        assertEquals(1, userService.resetPassword(email));
    }

    @Test
    void resetPasswordByTokenShouldReturnZeroIfTokenDoesNotExist() {
        //given
        String value = "sampletokenvalue-2131-loremipsumloremipsum";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.resetPasswordByToken(value));
    }

    @Test
    void resetPasswordByTokenShouldReturnOneIfTokenHasExpired() {
        //given
        String value = "sampletokenvalue-2131-loremipsumloremipsum";
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().minusMinutes(10));

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertEquals(1, userService.resetPasswordByToken(value));
    }

    @Test
    void resetPasswordByTokenShouldReturnTwoIfTokenWasFoundAndConfirmed() {
        //given
        String value = "sampletokenvalue-2131-loremipsumloremipsum";
        Token token = new Token();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertEquals(2, userService.resetPasswordByToken(value));
    }

    @Test
    void findUserByTokenValueShouldReturnZeroIfTokenDoesNotExist() {
        //given
        String value = "sampletokenvalue-2131-loremipsumloremipsum";

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> userService.findUserByTokenValue(value));
    }

    @Test
    void findUserByTokenValueShouldReturnTokenUserIfTokenExists() {
        //given
        String value = "sampletokenvalue-2131-loremipsumloremipsum";
        User user = new User();
        Token token = new Token(user, value, LocalDateTime.now().plusMinutes(60));

        //when
        when(tokenRepo.findByValue(value)).thenReturn(Optional.of(token));

        //then
        assertEquals(user, userService.findUserByTokenValue(value));
    }

    @Test
    void updateNewPasswordUserShouldReturnZeroIfUserDoesNotExist() {
        //given
        User newPasswordUser = new User();

        //when
        when(userRepo.findByUsername(newPasswordUser.getUsername())).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.updateNewPasswordUser(newPasswordUser));
    }

    @Test
    void updateNewPasswordUserShouldReturnOneIfUserWithNewPasswordWasSaved() {
        //given
        User newPasswordUser = new User();
        newPasswordUser.setPassword("newpassword123");

        //when
        when(userRepo.findByUsername(newPasswordUser.getUsername())).thenReturn(Optional.of(newPasswordUser));

        //then
        assertEquals(1, userService.updateNewPasswordUser(newPasswordUser));
    }

}