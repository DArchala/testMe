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
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.service.UserAuthService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

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
}