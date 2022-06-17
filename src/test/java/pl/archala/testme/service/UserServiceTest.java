package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.dto.DataTableSortPage;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.archala.testme.enums.RoleEnum.ADMIN;
import static pl.archala.testme.enums.RoleEnum.USER;

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
    void findUserByUsernameShouldThrowExceptionIfUserDoesNotExist() {
        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByUsername("username"));
    }

    @Test
    void findUserByUsernameShouldReturnUser() {
        //given
        User user = new User();

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));

        //then
        assertEquals(user, userService.findUserByUsername("username"));
    }

    @Test
    void deleteUserShouldThrowExceptionIfUserDoesNotExist() {
        //when
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void deleteUserShouldThrowExceptionDeletingLastAdminIsForbidden() {
        //when
        User user = new User();
        user.setRole(ADMIN);

        //when
        when(userRepo.findAll()).thenReturn(List.of(user));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void deleteUserShouldNotThrowAnyException() {
        //when
        User user = new User();
        User user2 = new User();
        user.setRole(ADMIN);
        user2.setRole(ADMIN);

        //when
        when(userRepo.findAll()).thenReturn(List.of(user, user2));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        //then
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void updateUserRoleShouldThrowExceptionIfUserDoesNotContainId() {
        //given
        User user = new User();

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldThrowExceptionIfUserDoesNotExist() {
        //given
        User user = new User();
        user.setId(1L);

        //when
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldThrowExceptionForDeletingLastAdmin() {
        //given
        User newUser = new User();
        newUser.setRole(USER);
        newUser.setId(1L);

        User currentUser = new User();
        currentUser.setRole(ADMIN);
        currentUser.setId(1L);

        //when
        when(userRepo.findById(1L)).thenReturn(Optional.of(currentUser));
        when(userRepo.findByRole(ADMIN)).thenReturn(List.of(currentUser));

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updateUserRole(newUser));
    }

    @Test
    void updateUserRoleShouldUpdateUserIfConditionsAreMet() {
        //given
        User newUser = new User();
        newUser.setRole(ADMIN);
        newUser.setId(1L);

        User currentUser = new User();
        currentUser.setRole(USER);
        currentUser.setId(1L);

        //when
        when(userRepo.findById(1L)).thenReturn(Optional.of(currentUser));

        //then
        assertDoesNotThrow(() -> userService.updateUserRole(newUser));
    }

    @Test
    void updatePasswordByRequestShouldThrowExceptionIfUserDoesNotExist() {
        //given
        var request = new PasswordChangeRequest();

        //when
        when(userRepo.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.updatePasswordByRequest(request));
    }

    @Test
    void updatePasswordByRequestShouldThrowExceptionIfProvidedPasswordDoNotMatchWithCurrentPassword() {
        //given
        var request = new PasswordChangeRequest();
        User user = new User();

        //when
        when(userRepo.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(false);

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePasswordByRequest(request));
    }

    @Test
    void updatePasswordByRequestShouldThrowExceptionIfProvidedEmailDoNotMatchWithCurrentEmail() {
        //given
        var request = new PasswordChangeRequest();
        request.setEmail("AAAAA@gmail.com");

        User user = new User();
        user.setEmail("BBBB@gmail.com");

        //when
        when(userRepo.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(true);

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePasswordByRequest(request));
    }

    @Test
    void updatePasswordByRequestShouldThrowExceptionIfProvidedPasswordIsEqualToCurrentPassword() {
        //given
        var request = new PasswordChangeRequest();
        request.setEmail("AAAAA@gmail.com");

        User user = new User();
        user.setEmail("AAAAA@gmail.com");

        //when
        when(userRepo.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(request.getNewPassword(), user.getPassword())).thenReturn(true);

        //then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePasswordByRequest(request));
    }

    @Test
    void updatePasswordByRequestShouldNotThrowAnyException() {
        //given
        var request = new PasswordChangeRequest();
        request.setEmail("AAAAA@gmail.com");
        request.setNewPassword("user2468");

        User user = new User();
        user.setEmail("AAAAA@gmail.com");
        user.setPassword("$2a$12$s7GnHP2rlMAI0GnMdhhr9.Izu1VFUVYPrrJCaj8hogk9h4JloRH3i");

        //when
        when(userRepo.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(true);

        //then
        assertDoesNotThrow(() -> userService.updatePasswordByRequest(request));
    }

    @Test
    void updateNewPasswordUserShouldThrowExceptionIfUserDoesNotExist() {
        //given
        User newPasswordUser = new User();
        newPasswordUser.setUsername("username");

        //when
        when(userRepo.findByUsername(newPasswordUser.getUsername())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> userService.updateNewPasswordUser(newPasswordUser));
    }

    @Test
    void updateNewPasswordUserShouldNoThrowAnyException() {
        //given
        User newPasswordUser = new User();
        newPasswordUser.setUsername("username");

        //when
        when(userRepo.findByUsername(newPasswordUser.getUsername())).thenReturn(Optional.of(newPasswordUser));

        //then
        assertDoesNotThrow(() -> userService.updateNewPasswordUser(newPasswordUser));
    }
}