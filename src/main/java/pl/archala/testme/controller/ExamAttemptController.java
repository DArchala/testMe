package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExamAttemptController {

    private final UserRepository userRepo;

    public ExamAttemptController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/examAttempts")
    public ResponseEntity<?> getMyExamAttempts(Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElse(null);
        if (user == null) return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);

        List<ExamAttempt> myExamAttempts = user.getExamAttempts();
        return new ResponseEntity<>(myExamAttempts, HttpStatus.OK);
    }
}
