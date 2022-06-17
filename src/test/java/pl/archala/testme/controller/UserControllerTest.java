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
import pl.archala.testme.dto.DataTableSortPage;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.service.UserService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
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
    void findAllUsersShouldReturnUsersList() {
        //given
        List<User> users = new ArrayList<>(List.of(new User()));
        ResponseEntity<?> response = new ResponseEntity<>(users, HttpStatus.OK);

        //when
        when(userRepo.findAll()).thenReturn(users);

        //then
        assertEquals(response, userController.findAllUsers());
    }

    @Test
    void findUserByUsernameShouldReturnUser() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>(user, HttpStatus.OK);

        //when
        when(userService.findUserByUsername("username")).thenReturn(user);

        //then
        assertEquals(response, userController.findUserByUsername("username"));
    }

    @Test
    void findUserByUsernameShouldReturnCaughtExceptionMessage() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userService).findUserByUsername("username");

        //then
        assertEquals(response, userController.findUserByUsername("username"));
    }

    @Test
    void updateUserRoleShouldReturnUserUpdated() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User updated", HttpStatus.OK);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateUserRoleShouldReturnCaughtExceptionMessage() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userService).updateUserRole(user);

        //then
        assertEquals(response, userController.updateUserRole(user));
    }

    @Test
    void updateUserPasswordShouldReturnUserUpdated() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User updated", HttpStatus.OK);

        //then
        assertEquals(response, userController.updateUserPassword(user));
    }

    @Test
    void updateUserPasswordShouldReturnCaughtExceptionMessage() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userService).updateNewPasswordUser(user);

        //then
        assertEquals(response, userController.updateUserPassword(user));
    }

    @Test
    void deleteUserShouldReturnUserDeleted() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User deleted", HttpStatus.OK);

        //then
        assertEquals(response, userController.deleteUser(1L));
    }

    @Test
    void deleteUserShouldReturnCaughtExceptionMessage() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userService).deleteUser(1L);

        //then
        assertEquals(response, userController.deleteUser(1L));
    }

    @Test
    void getRolesShouldReturnRoleEnumValues() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>(RoleEnum.values(), HttpStatus.OK);

        //when
        assertEquals(response, userController.getRoles());
    }

    @Test
    void changeMyPasswordShouldReturnUserSaved() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("User updated", HttpStatus.OK);

        //when
        assertEquals(response, userController.changeMyPassword(new PasswordChangeRequest()));
    }

    @Test
    void changeMyPasswordShouldReturnCaughtExceptionMessage() {
        //given
        var passwordChangeRequest = new PasswordChangeRequest();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);

        //when
        doThrow(new EntityNotFoundException("User does not exist")).when(userService).updatePasswordByRequest(passwordChangeRequest);

        //when
        assertEquals(response, userController.changeMyPassword(passwordChangeRequest));
    }

    @Test
    void findAllUsersPaginatedShouldReturnUsersList() {
        //given
        var dataTableSortPage = new DataTableSortPage();
        List<User> users = new ArrayList<>(List.of(new User()));
        ResponseEntity<?> response = new ResponseEntity<>(users, HttpStatus.OK);

        //when
        when(userService.findAllUsersPaginated(dataTableSortPage)).thenReturn(users);

        //then
        assertEquals(response, userController.findAllUsersPaginated(dataTableSortPage));
    }


}