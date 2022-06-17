package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.dto.DataTableSortPage;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.service.UserService;

import javax.validation.Valid;
import java.util.List;

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
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping("/findBy/username")
    public ResponseEntity<?> findUserByUsername(@RequestBody String username) {
        try {
            User user = userService.findUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/role")
    public ResponseEntity<?> updateUserRole(@RequestBody @Valid User user) {
        try {
            userService.updateUserRole(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> updateUserPassword(@RequestBody @Valid User user) {
        try {
            userService.updateNewPasswordUser(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/activate/token")
    public ResponseEntity<?> activateAccountByToken(@RequestParam String value) {
        try {
            userService.activateAccountByToken(value);
            return new ResponseEntity<>("User account is now enable. You can log in.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/password/reset/token")
    public ResponseEntity<?> confirmPasswordResetByToken(@RequestParam String value) {
        try {
            userService.resetPasswordByToken(value);
            return new ResponseEntity<>(userService.findUserByTokenValue(value), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return new ResponseEntity<>(RoleEnum.values(), HttpStatus.OK);
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changeMyPassword(@RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        try {
            userService.updatePasswordByRequest(passwordChangeRequest);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findAll/paginated")
    public ResponseEntity<?> findAllUsersPaginated(@RequestBody @Valid DataTableSortPage dtSortPage) {
        List<User> usersPaginated = userService.findAllUsersPaginated(dtSortPage);
        return new ResponseEntity<>(usersPaginated, HttpStatus.OK);
    }
}