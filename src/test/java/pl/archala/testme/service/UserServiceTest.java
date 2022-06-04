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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    void registerUserShouldReturnZeroIfExistUserFoundByUsername() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        //then
        assertEquals(0, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnMinusOneIfExistUserFoundByEmail() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //then
        assertEquals(-1, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnOneIfUserNotExist() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertEquals(1, userService.registerUser(user));
    }

    @Test
    void registerUserShouldInvokeSetPasswordAndSaveUser() {
        //given
        User user = mock(User.class);

        //when
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(user.getUsername()).thenReturn("username");
        when(user.getPassword()).thenReturn("password");
        when(user.getEmail()).thenReturn("email@gmail.com");

        userService.registerUser(user);

        //then
        verify(user).setPassword(passwordEncoder.encode(user.getPassword()));
        verify(userRepo).save(user);
    }

}