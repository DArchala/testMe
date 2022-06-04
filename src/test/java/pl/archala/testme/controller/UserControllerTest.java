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
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.security.Token;
import pl.archala.testme.service.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    void registerUserShouldReturnCorrectMessageAndStatusCreatedIfServiceReturnOne() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User registered, but still not active. Check your mailbox.",
                HttpStatus.CREATED);
        User sampleUser = new User();

        //when
        when(userService.registerUser(sampleUser)).thenReturn(1);

        //then
        assertEquals(response, userController.registerUser(sampleUser));
    }

    @Test
    void registerUserShouldReturnUsernameIsTakenMessageAndStatusBadRequestIfUserServiceReturnZero() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("This username is already taken",
                HttpStatus.BAD_REQUEST);
        User sampleUser = new User();

        //when
        when(userService.registerUser(sampleUser)).thenReturn(0);

        //then
        assertEquals(response, userController.registerUser(sampleUser));
    }

    @Test
    void registerUserShouldReturnEmailIsTakenMessageAndStatusBadRequestIfUserServiceReturnMinusOne() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("This email is already taken",
                HttpStatus.BAD_REQUEST);
        User sampleUser = new User();

        //when
        when(userService.registerUser(sampleUser)).thenReturn(-1);

        //then
        assertEquals(response, userController.registerUser(sampleUser));
    }

    @Test
    void registerUserShouldReturnUndefinedErrorAndInternalErrorStatusAsDefaultValue() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Undefined error",
                HttpStatus.INTERNAL_SERVER_ERROR);
        User sampleUser = new User();

        //when
        when(userService.registerUser(sampleUser)).thenReturn(5);

        //then
        assertEquals(response, userController.registerUser(sampleUser));
    }

    @Test
    void sendTokenShouldReturnNotFoundIfTokenDoesNotExist() {
        //given
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Token does not exist", HttpStatus.NOT_FOUND);

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.empty());

        //then
        assertEquals(response, userController.sendToken(tokenValue));
    }

    @Test
    void sendTokenShouldReturnNotFoundIfTokenHasNoUser() {
        //given
        Token token = new Token();
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("Token has no user", HttpStatus.NOT_FOUND);

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.of(token));

        //then
        assertEquals(response, userController.sendToken(tokenValue));
    }

    @Test
    void sendTokenShouldReturnOKStatusIfUserWasEnabled() {
        //given
        Token token = new Token();
        token.setUser(new User());
        String tokenValue = UUID.randomUUID().toString();
        ResponseEntity<?> response = new ResponseEntity<>("User account is now enable.", HttpStatus.OK);

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.of(token));

        //then
        assertEquals(response, userController.sendToken(tokenValue));
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
}