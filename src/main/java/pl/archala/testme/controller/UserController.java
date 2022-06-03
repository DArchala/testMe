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
                return new ResponseEntity<>("User registered, but still not active. Check your mailbox.", HttpStatus.CREATED);
            case 0:
                return new ResponseEntity<>("This username is already taken", HttpStatus.BAD_REQUEST);
            case -1:
                return new ResponseEntity<>("This email is already taken", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Undefined error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/token")
    public ResponseEntity<?> sendToken(@RequestParam String value) {
        Token token = tokenRepo.findByValue(value).orElse(null);
        if (token == null) return new ResponseEntity<>("Token does not exist", HttpStatus.NOT_FOUND);

        User user = token.getUser();
        if (user == null) return new ResponseEntity<>("Token has no user", HttpStatus.NOT_FOUND);

        user.setEnabled(true);
        userRepo.save(user);
        return new ResponseEntity<>("User account is now enable.", HttpStatus.OK);
    }

    @PostMapping("/findByUsername")
    public ResponseEntity<?> findUserByUsername(@RequestBody String username) {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}