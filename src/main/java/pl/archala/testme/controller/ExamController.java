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
import java.util.List;
import java.util.Optional;

import static pl.archala.testme.component.CustomResponseEntity.*;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class ExamController {

    private final ExamRepository examRepo;
    private final ExamService examService;

    public ExamController(ExamRepository examRepo, ExamService examService) {
        this.examRepo = examRepo;
        this.examService = examService;
    }

    @GetMapping("/exams")
    public ResponseEntity<?> getExams() {
        List<Exam> exams = examService.getAllExams();
        if (exams.isEmpty()) return NO_EXAMS_FOUND;
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PostMapping("/exams/exam")
    public ResponseEntity<?> checkExamCorrectness(@RequestBody @Valid ExamForm examForm, Principal principal) {
        if (examService.saveExamAttemptToUser(examForm, principal.getName()))
            return USER_DOES_NOT_EXIST;
        int score = examService.countUserExamPoints(examForm.getExam());
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping("/exams/exam/{id}")
    public ResponseEntity<?> getExamById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return EXAM_DOES_NOT_EXIST;
        return new ResponseEntity<>(exam.get().setAllAnswersFalse(), HttpStatus.OK);
    }

    @PostMapping("/exams/exam/{id}")
    public ResponseEntity<?> getExamMaxPoints(@PathVariable("id") Long id) {
        if (examRepo.findById(id).isEmpty())
            return EXAM_DOES_NOT_EXIST;
        int examMaxPoints = examService.getMaxPossibleExamPoints(id);
        return new ResponseEntity<>(examMaxPoints, HttpStatus.OK);
    }

    @GetMapping("/new-exam")
    public ResponseEntity<ExamDifficultyLevel[]> getNewExamData() {
        return new ResponseEntity<>(ExamDifficultyLevel.values(), HttpStatus.OK);
    }

    @PostMapping("/new-exam/save")
    public ResponseEntity<?> saveNewExam(@RequestBody @Valid Exam exam) {
        switch (examService.saveNewExam(exam)) {
            case 0:
                return EXAM_NAME_ALREADY_TAKEN;
            case 1:
                return EXAM_ANY_QUESTION_DOES_NOT_CONTAIN_ANY_CORRECT_ANSWER;
            case 2:
                return EXAM_SAVED;
            default:
                return SAVING_EXAM_FAILED;
        }
    }

    @GetMapping("/exams/edit/{id}")
    public ResponseEntity<?> getExamToEditById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return EXAM_DOES_NOT_EXIST;
        return new ResponseEntity<>(exam.get(), HttpStatus.OK);
    }

    @PutMapping("/exams/edit")
    public ResponseEntity<?> putExam(@RequestBody @Valid Exam exam) {
        if (examService.putExam(exam)) return EXAM_SAVED;
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/exams/delete/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable("id") Long id) {
        if (examService.deleteExam(id)) return EXAM_DELETED;
        return EXAM_DOES_NOT_EXIST;
    }

}
