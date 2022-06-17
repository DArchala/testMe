package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.service.ExamAttemptService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExamAttemptController {

    private final ExamAttemptService examAttemptService;

    public ExamAttemptController(ExamAttemptService examAttemptService) {
        this.examAttemptService = examAttemptService;
    }

    @GetMapping("/exam-attempts")
    public ResponseEntity<?> getMyExamAttempts(Principal principal) {
        List<ExamAttempt> myExamAttempts = examAttemptService.findExamAttemptsByUsername(principal.getName());
        return new ResponseEntity<>(myExamAttempts, HttpStatus.OK);
    }
}
