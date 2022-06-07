package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    void registerUserShouldReturnZeroIfUserFoundByUsername() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        //then
        assertEquals(0, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnOneIfUserFoundByEmail() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //then
        assertEquals(1, userService.registerUser(user));
    }

    @Test
    void registerUserShouldReturnTwoIfUserSavedAndTokenWasSent() {
        //given
        User user = new User("user", "password", "email@gmail.com", RoleEnum.USER, true, null);

        //when
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //then
        assertEquals(2, userService.registerUser(user));
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
    void activateAccountShouldReturnZeroIfTokenDoesNotExist() {
        //given
        String tokenValue = "sample-token-value-123";

        //when
        when(tokenRepo.findByValue(tokenValue)).thenReturn(Optional.empty());

        //then
        assertEquals(0, userService.activateAccount(tokenValue));
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
        assertEquals(1, userService.activateAccount(tokenValue));
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
        assertEquals(2, userService.activateAccount(tokenValue));
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
        assertEquals(3, userService.activateAccount(tokenValue));
    }


}