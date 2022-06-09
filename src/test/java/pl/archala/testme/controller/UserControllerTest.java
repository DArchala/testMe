package pl.archala.testme.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private TokenRepository tokenRepo;

    @Test
    void registerUserShouldReturnUsernameAlreadyTakenIfServiceReturnZero() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("This username is already taken.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.registerUser(user)).thenReturn(0);

        //then
        assertEquals(response, userController.registerUser(user));
    }

    @Test
    void registerUserShouldReturnEmailAlreadyTakenIfServiceReturnOne() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("This e-mail is already taken.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.registerUser(user)).thenReturn(1);

        //then
        assertEquals(response, userController.registerUser(user));
    }

    @Test
    void registerUserShouldReturnUserRegisteredCheckMailboxIfServiceReturnTwo() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User registered, but still not active - check your mailbox.", HttpStatus.CREATED);

        //when
        when(userService.registerUser(user)).thenReturn(2);

        //then
        assertEquals(response, userController.registerUser(user));
    }

    @Test
    void registerUserShouldReturnUndefinedErrorAndInternalErrorStatusAsDefaultValue() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.",
                HttpStatus.INTERNAL_SERVER_ERROR);
        User sampleUser = new User();

        //when
        when(userService.registerUser(sampleUser)).thenReturn(-1);

        //then
        assertEquals(response, userController.registerUser(sampleUser));
    }

    @Test
    void activateAccountByTokenShouldReturnTokenDoesNotExistIfServiceReturnZero() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Token does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.activateAccountByToken(tokenValue)).thenReturn(0);

        //then
        assertEquals(response, userController.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountByTokenShouldReturnTokenHasExpiredIfServiceReturnOne() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Your token has expired.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.activateAccountByToken(tokenValue)).thenReturn(1);

        //then
        assertEquals(response, userController.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountByTokenShouldReturnTokenHasNoUserIfServiceReturnTwo() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Token has no user.", HttpStatus.NOT_FOUND);

        //when
        when(userService.activateAccountByToken(tokenValue)).thenReturn(2);

        //then
        assertEquals(response, userController.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountByTokenShouldReturnAccountEnabledIfServiceReturnThree() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("User account is now enable. You can log in.", HttpStatus.OK);

        //when
        when(userService.activateAccountByToken(tokenValue)).thenReturn(3);

        //then
        assertEquals(response, userController.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountByTokenShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.activateAccountByToken(tokenValue)).thenReturn(-1);

        //then
        assertEquals(response, userController.activateAccountByToken(tokenValue));
    }

    @Test
    void findAllUsersShouldReturnUsersList() {
        //given
        List<User> userList = new ArrayList<>();
        ResponseEntity<?> response = new ResponseEntity<>(userList, HttpStatus.OK);

        //when
        when(userRepo.findAll()).thenReturn(userList);

        //then
        assertEquals(response, userController.findAllUsers());
    }

    @Test
    void findUserByUsernameShouldReturnUserAndOKStatusIfUserExists() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>(user, HttpStatus.OK);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        //then
        assertEquals(response, userController.findUserByUsername(user.getUsername()));

    }

    @Test
    void findUserByUsernameShouldReturnNotFoundStatusIfUserDoesNotExist() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //when
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());

        //then
        assertEquals(response, userController.findUserByUsername(anyString()));

    }

    @Test
    void deleteUserShouldReturnUserDoesNotExistIfServiceReturnZero() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.deleteUser(anyLong())).thenReturn(0);

        //then
        assertEquals(response, userController.deleteUser(anyLong()));
    }

    @Test
    void deleteUserShouldReturnDeletingLastAdminForbiddenIfServiceReturnOne() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Deleting last admin is forbidden.", HttpStatus.FORBIDDEN);

        //when
        when(userService.deleteUser(anyLong())).thenReturn(1);

        //then
        assertEquals(response, userController.deleteUser(anyLong()));
    }

    @Test
    void deleteUserShouldReturnUserDeletedIfServiceReturnTwo() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User deleted.", HttpStatus.OK);

        //when
        when(userService.deleteUser(anyLong())).thenReturn(2);

        //then
        assertEquals(response, userController.deleteUser(anyLong()));
    }

    @Test
    void deleteUserShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.deleteUser(anyLong())).thenReturn(-1);

        //then
        assertEquals(response, userController.deleteUser(anyLong()));
    }

    @Test
    void getRolesShouldReturnRoleEnumValues() {
        ResponseEntity<?> response = new ResponseEntity<>(RoleEnum.values(), HttpStatus.OK);
        assertEquals(response, userController.getRoles());
    }

    @Test
    void updateUserRoleShouldReturnUserDoesNotExistIfServiceReturnZero() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.updateUserRole(user)).thenReturn(0);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnUserSavedIfServiceReturnOne() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User saved.", HttpStatus.OK);

        //when
        when(userService.updateUserRole(user)).thenReturn(1);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.updateUserRole(user)).thenReturn(-1);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnDeletingLastAdminForbiddenIfServiceReturnTwo() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("Deleting last admin is forbidden.", HttpStatus.FORBIDDEN);

        //when
        when(userService.updateUserRole(user)).thenReturn(2);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateNewPasswordUserShouldReturnUserDoesNotExistIfServiceReturnZero() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.updateNewPasswordUser(user)).thenReturn(0);

        //then
        assertEquals(response, userController.updateUserPassword(user));
    }

    @Test
    void updateNewPasswordUserShouldReturnUserSavedIfServiceReturnOne() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User saved.", HttpStatus.OK);

        //when
        when(userService.updateNewPasswordUser(user)).thenReturn(1);

        //then
        assertEquals(response, userController.updateUserPassword(user));
    }

    @Test
    void updateNewPasswordUserShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.updateNewPasswordUser(user)).thenReturn(-1);

        //then
        assertEquals(response, userController.updateUserPassword(user));
    }


    @Test
    void confirmPasswordResetByTokenShouldReturnTokenDoesNotExistIfServiceReturnZero() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Token does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.resetPasswordByToken("value")).thenReturn(0);

        //then
        assertEquals(response, userController.confirmPasswordResetByToken("value"));
    }

    @Test
    void confirmPasswordResetByTokenShouldReturnTokenHasExpiredIfServiceReturnOne() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Your token has expired.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.resetPasswordByToken("value")).thenReturn(1);

        //then
        assertEquals(response, userController.confirmPasswordResetByToken("value"));
    }

    @Test
    void confirmPasswordResetByTokenShouldReturnUserAndOKStatusIfServiceReturnTwo() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>(user, HttpStatus.OK);

        //when
        when(userService.resetPasswordByToken("value")).thenReturn(2);
        when(userService.findUserByTokenValue("value")).thenReturn(user);

        //then
        assertEquals(response, userController.confirmPasswordResetByToken("value"));
    }

    @Test
    void confirmPasswordResetByTokenShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.resetPasswordByToken("value")).thenReturn(-1);

        //then
        assertEquals(response, userController.confirmPasswordResetByToken("value"));
    }

    @Test
    void changeMyPasswordShouldReturnUserDoesNotExistIfServiceReturnZero() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(0);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void changeMyPasswordShouldReturnPasswordDoesNotMatchIfServiceReturnOne() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("The password and password provided for the account do not match", HttpStatus.BAD_REQUEST);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(1);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void changeMyPasswordShouldReturnUsernameDoNotMatchWithEmailIfServiceReturnTwo() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("Username do not match with user email", HttpStatus.BAD_REQUEST);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(2);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void changeMyPasswordShouldReturnNewPasswordCannotBeEqualToOldPasswordIfServiceReturnThree() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("New password cannot be equal to old password.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(3);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void changeMyPasswordShouldReturnUserSavedIfServiceReturnFour() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("User saved.", HttpStatus.OK);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(4);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void changeMyPasswordShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.updatePassword(passwordChangeRequest)).thenReturn(-1);

        //then
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void resetPasswordShouldReturnUserDoesNotExistIfServiceReturnZero() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userService.resetPassword(email)).thenReturn(0);

        //then
        assertEquals(response, userController.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnPasswordResetLinkWasSentCheckMailboxIfServiceReturnOne() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response = new ResponseEntity<>("Password reset link was sent - check mailbox.", HttpStatus.BAD_REQUEST);

        //when
        when(userService.resetPassword(email)).thenReturn(1);

        //then
        assertEquals(response, userController.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnPasswordResettingForDisabledUserIsForbiddenIfServiceReturnTwo() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response = new ResponseEntity<>("Password resetting for disabled user is forbidden.", HttpStatus.FORBIDDEN);

        //when
        when(userService.resetPassword(email)).thenReturn(2);

        //then
        assertEquals(response, userController.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnUndefinedErrorAsDefaultValue() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error.", HttpStatus.INTERNAL_SERVER_ERROR);

        //when
        when(userService.resetPassword(email)).thenReturn(-1);

        //then
        assertEquals(response, userController.resetPassword(email));
    }
}