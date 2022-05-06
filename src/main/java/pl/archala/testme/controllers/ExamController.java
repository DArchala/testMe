package pl.archala.testme.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;
import pl.archala.testme.service.AnswerService;
import pl.archala.testme.service.ExamService;
import pl.archala.testme.service.QuestionService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/exams", produces = "application/json")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository examRepo;
    private final ExamService examService;
    private final QuestionRepository questionRepo;
    private final QuestionService questionService;
    private final AnswerRepository answerRepo;
    private final AnswerService answerService;

    public ExamController(ExamRepository examRepo, ExamService examService, QuestionRepository questionRepo, QuestionService questionService, AnswerRepository answerRepo, AnswerService answerService) {
        this.examRepo = examRepo;
        this.examService = examService;
        this.questionRepo = questionRepo;
        this.questionService = questionService;
        this.answerRepo = answerRepo;
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getExams() {
        List<Exam> exams = (List<Exam>) examRepo.findAll();
        ResponseEntity<List<Exam>> response = new ResponseEntity<>(exams, HttpStatus.OK);
        return response;
    }

    @PostMapping("/exam")
    public int checkExamCorrectness(@RequestBody Exam exam) {
        return questionService.countUserExamPoints(exam);
    }

    @GetMapping("/exam/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exam.get().setAllAnswersFalse(), HttpStatus.OK);
    }

}
