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
import pl.archala.testme.entity.User;
import pl.archala.testme.service.UserAuthService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class UserAuthControllerTest {

    @InjectMocks
    private UserAuthController userAuthController;

    @Mock
    private UserAuthService userAuthService;

    @Test
    void registerUserShouldReturnUserRegisteredCheckMailbox() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User registered, but still not active - check your mailbox.", HttpStatus.OK);

        //then
        assertEquals(response, userAuthController.registerUser(user));
    }

    @Test
    void registerUserShouldReturnCaughtExceptionMessage() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);

        //
        doThrow(new EntityExistsException("Username is already taken")).when(userAuthService).registerUser(user);

        //then
        assertEquals(response, userAuthController.registerUser(user));
    }

    @Test
    void resetPasswordShouldReturnPasswordResetLinkWasSent() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response  = new ResponseEntity<>("Password reset link was sent - check mailbox.", HttpStatus.OK);

        //then
        assertEquals(response, userAuthController.resetPassword(email));
    }

    @Test
    void resetPasswordShouldReturnCaughtExceptionMessage() {
        //given
        String email = "email@gmail.com";
        ResponseEntity<?> response  = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userAuthService).resetPassword(email);

        //then
        assertEquals(response, userAuthController.resetPassword(email));
    }

    @Test
    void activateAccountByTokenShouldReturnUserAccountIsEnable() {
        //given
        String tokenValue = "tokenvalue!#&$*#(!1111222";
        ResponseEntity<?> response = new ResponseEntity<>("User account is now enable. You can log in.", HttpStatus.OK);

        //when
        assertEquals(response, userAuthController.activateAccountByToken(tokenValue));
    }

    @Test
    void activateAccountByTokenShouldReturnCaughtExceptionMessage() {
        //given
        String tokenValue = "tokenvalue!#&$*#(!1111222";
        ResponseEntity<?> response = new ResponseEntity<>("Token does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("Token does not exist")).when(userAuthService).activateAccountByToken(tokenValue);

        //when
        assertEquals(response, userAuthController.activateAccountByToken(tokenValue));
    }

    @Test
    void confirmPasswordResetByTokenShouldReturnUser() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>(user, HttpStatus.OK);

        //when
        when(userAuthService.findUserByTokenValue("value")).thenReturn(user);

        //then
        assertEquals(response, userAuthController.confirmPasswordResetByToken("value"));
    }

    @Test
    void confirmPasswordResetByTokenShouldReturnCaughtExceptionMessage() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("Token does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("Token does not exist")).when(userAuthService).resetPasswordByToken("value");

        //then
        assertEquals(response, userAuthController.confirmPasswordResetByToken("value"));
    }
}