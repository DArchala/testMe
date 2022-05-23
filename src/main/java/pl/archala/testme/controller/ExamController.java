package pl.archala.testme.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.ExamDifficultyLevel;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.service.ExamService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository examRepo;
    private final ExamService examService;

    public ExamController(ExamRepository examRepo, ExamService examService) {
        this.examRepo = examRepo;
        this.examService = examService;
    }

    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getExams() {
        List<Exam> exams = examService.getAllExams();
        if (exams.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PostMapping("/exams/exam")
    public int checkExamCorrectness(@RequestBody Exam exam) {
        return examService.countUserExamPoints(exam);
    }

    @GetMapping("/exams/exam/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exam.get().setAllAnswersFalse(), HttpStatus.OK);
    }

    @PostMapping("/exams/exam/{id}")
    public int getExamMaxPoints(@PathVariable("id") Long id) {
        return examService.getMaxPossibleExamPoints(id);
    }

    @GetMapping("/new-exam")
    public ResponseEntity<ExamDifficultyLevel[]> getNewExamData() {
        return new ResponseEntity<>(ExamDifficultyLevel.values(), HttpStatus.OK);
    }

    @PostMapping("/new-exam/save")
    public ResponseEntity<Exam> saveNewExam(@RequestBody Exam exam) {
        if (examService.saveNewExam(exam)) return new ResponseEntity<>(exam, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/exams/edit/{id}")
    public ResponseEntity<Exam> getExamToEditById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exam.get(), HttpStatus.OK);
    }

    @PutMapping("/exams/edit")
    public ResponseEntity<?> putExam(@RequestBody Exam exam) {
        log.info(exam.toString());
        if(examService.putExam(exam)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
