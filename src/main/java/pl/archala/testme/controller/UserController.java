package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.security.Token;
import pl.archala.testme.service.UserService;

import static pl.archala.testme.component.CustomResponseEntity.*;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepo;

    private final TokenRepository tokenRepo;

    public UserController(UserService userService, UserRepository userRepo, TokenRepository tokenRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        switch (userService.registerUser(user)) {
            case 1:
                return USER_REGISTERED_CHECK_MAILBOX;
            case 0:
                return USERNAME_ALREADY_TAKEN;
            case -1:
                return EMAIL_ALREADY_TAKEN;
            default:
                return UNDEFINED_ERROR;
        }
    }

    @GetMapping("/token")
    public ResponseEntity<?> sendToken(@RequestParam String value) {
        Token token = tokenRepo.findByValue(value).orElse(null);
        if (token == null) return TOKEN_DOES_NOT_EXIST;

        User user = token.getUser();
        if (user == null) return TOKEN_HAS_NO_USER;

        user.setEnabled(true);
        userRepo.save(user);
        return ACCOUNT_ENABLED;
    }

    @PostMapping("/findByUsername")
    public ResponseEntity<?> findUserByUsername(@RequestBody String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}