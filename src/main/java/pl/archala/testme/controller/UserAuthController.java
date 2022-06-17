package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.User;
import pl.archala.testme.service.UserAuthService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        try {
            userAuthService.registerUser(user);
            return new ResponseEntity<>("User registered, but still not active - check your mailbox.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody String email) {
        try {
            userAuthService.resetPassword(email);
            return new ResponseEntity<>("Password reset link was sent - check mailbox.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
