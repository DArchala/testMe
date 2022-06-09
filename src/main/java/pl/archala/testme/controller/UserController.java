package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static pl.archala.testme.component.CustomResponseEntity.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepo;

    public UserController(UserService userService, UserRepository userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        List<User> users = userRepo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/findBy/username")
    public ResponseEntity<?> findUserByUsername(@RequestBody String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/role")
    public ResponseEntity<?> updateUserRole(@RequestBody @Valid User user) {
        switch (userService.updateUserRole(user)) {
            case 0:
                return USER_DOES_NOT_EXIST;
            case 1:
                return USER_SAVED;
            case 2:
                return DELETING_LAST_ADMIN_FORBIDDEN;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody @Valid User user) {
        switch (userService.updateNewPasswordUser(user)) {
            case 0:
                return USER_DOES_NOT_EXIST;
            case 1:
                return USER_SAVED;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        switch (userService.deleteUser(id)) {
            case 0:
                return USER_DOES_NOT_EXIST;
            case 1:
                return DELETING_LAST_ADMIN_FORBIDDEN;
            case 2:
                return USER_DELETED;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        switch (userService.registerUser(user)) {
            case 0:
                return USERNAME_ALREADY_TAKEN;
            case 1:
                return EMAIL_ALREADY_TAKEN;
            case 2:
                return USER_REGISTERED_CHECK_MAILBOX;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @GetMapping("/activateAccount/token")
    public ResponseEntity<?> activateAccountByToken(@RequestParam String value) {
        switch (userService.activateAccountByToken(value)) {
            case 0:
                return TOKEN_DOES_NOT_EXIST;
            case 1:
                return TOKEN_HAS_EXPIRED;
            case 2:
                return TOKEN_HAS_NO_USER;
            case 3:
                return ACCOUNT_ENABLED;
            default:
                return UNDEFINED_ERROR;
        }

    }

    @GetMapping("/passwordReset/token")
    public ResponseEntity<?> confirmPasswordResetByToken(@RequestParam String value) {
        switch (userService.resetPasswordByToken(value)) {
            case 0:
                return TOKEN_DOES_NOT_EXIST;
            case 1:
                return TOKEN_HAS_EXPIRED;
            case 2:
                return new ResponseEntity<>(userService.findUserByTokenValue(value), HttpStatus.OK);
            default:
                return UNDEFINED_ERROR;
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return new ResponseEntity<>(RoleEnum.values(), HttpStatus.OK);
    }

    @PostMapping("/changeMyPassword")
    public ResponseEntity<?> changeMyPassword(@RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        switch (userService.updatePassword(passwordChangeRequest)) {
            case 0:
                return USER_DOES_NOT_EXIST;
            case 1:
                return PASSWORD_DOES_NOT_MATCH;
            case 2:
                return USERNAME_DO_NOT_MATCH_WITH_EMAIL;
            case 3:
                return NEW_PASSWORD_CANNOT_BE_EQUAL_TO_OLD_PASSWORD;
            case 4:
                return USER_SAVED;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @PostMapping("/passwordResetRequest")
    public ResponseEntity<?> resetPassword(@RequestBody String email) {
        switch (userService.resetPassword(email)) {
            case 0:
                return USER_DOES_NOT_EXIST;
            case 1:
                return PASSWORD_RESET_LINK_WAS_SENT_CHECK_MAILBOX;
            case 2:
                return PASSWORD_RESETTING_FOR_DISABLED_USER_IS_FORBIDDEN;
            default:
                return UNDEFINED_ERROR;
        }
    }
}