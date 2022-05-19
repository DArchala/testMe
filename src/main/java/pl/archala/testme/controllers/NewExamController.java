package pl.archala.testme.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.ExamDifficultyLevel;
import pl.archala.testme.service.ExamService;

@Slf4j
@RestController
@RequestMapping(path = "/new-exam", produces = "application/json")
@CrossOrigin(origins = "*")
public class NewExamController {

    private final ExamService examService;

    public NewExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<ExamDifficultyLevel[]> getNewExamData() {
        return new ResponseEntity<>(ExamDifficultyLevel.values(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Exam> saveNewExam(@RequestBody Exam exam) {
        if (examService.saveNewExam(exam)) return new ResponseEntity<>(exam, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
