package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.component.ExamForm;
import pl.archala.testme.enums.ExamDifficultyLevel;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.service.ExamService;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(path = "/api/exams", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<?> getExams() {
        return new ResponseEntity<>(examService.getAllExams(), HttpStatus.OK);
    }

    @GetMapping("/exam/{id}")
    public ResponseEntity<?> getExamById(@PathVariable("id") Long id) {
        try {
            Exam exam = examService.findExamById(id);
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exam/take/{id}")
    public ResponseEntity<?> getExamByIdToTake(@PathVariable("id") Long id) {
        try {
            Exam exam = examService.findExamById(id);
            return new ResponseEntity<>(exam.setAllAnswersFalse(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exam")
    public ResponseEntity<?> getExamScore(@RequestBody @Valid ExamForm examForm, Principal principal) {
        try {
            examService.saveExamAttemptToUser(examForm, principal.getName());
            int score = examService.countUserExamPoints(examForm.getExam());
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exam/max-points")
    public ResponseEntity<?> getExamMaxPoints(@RequestBody Long id) {
        try {
            int examMaxPoints = examService.getMaxPossibleExamPoints(id);
            return new ResponseEntity<>(examMaxPoints, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/new-exam")
    public ResponseEntity<ExamDifficultyLevel[]> getExamDifficultyLevels() {
        return new ResponseEntity<>(ExamDifficultyLevel.values(), HttpStatus.OK);
    }

    @PostMapping("/new-exam/save")
    public ResponseEntity<?> saveNewExam(@RequestBody @Valid Exam exam) {
        try {
            examService.saveNewExam(exam);
            return new ResponseEntity<>("Exam saved", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> putExam(@RequestBody @Valid Exam exam) {
        try {
            examService.putExam(exam);
            return new ResponseEntity<>("Exam saved", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable("id") Long id) {
        try {
            examService.deleteExam(id);
            return new ResponseEntity<>("Exam deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
