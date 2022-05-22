package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.service.ExamService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/exams", produces = "application/json")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository examRepo;
    private final ExamService examService;

    public ExamController(ExamRepository examRepo, ExamService examService) {
        this.examRepo = examRepo;
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getExams() {
        List<Exam> exams = examService.getAllExams();
        if (exams.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PostMapping("/exam")
    public int checkExamCorrectness(@RequestBody Exam exam) {
        return examService.countUserExamPoints(exam);
    }

    @PostMapping("/exam/{id}")
    public int getExamMaxPoints(@PathVariable("id") Long id) {
        return examService.getMaxPossibleExamPoints(id);
    }

    @GetMapping("/exam/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exam.get().setAllAnswersFalse(), HttpStatus.OK);
    }

}
