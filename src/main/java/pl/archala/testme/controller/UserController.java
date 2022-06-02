package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.User;
import pl.archala.testme.security.Token;
import pl.archala.testme.service.TokenService;
import pl.archala.testme.service.UserService;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.saveUser(user)) return new ResponseEntity<>("User saved", HttpStatus.CREATED);
        else return new ResponseEntity<>("User not saved", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/token")
    public ResponseEntity<?> sendToken(@RequestParam String value) {
        Token token = tokenService.findByValue(value);
        User user = token.getUser();
        user.setEnabled(true);
        userService.saveUser(user);
        return null;
    }

    @PostMapping("/findByUsername")
    public ResponseEntity<?> findUserByUsername(@RequestBody String username) {
        User user = userService.findByUsername(username);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
