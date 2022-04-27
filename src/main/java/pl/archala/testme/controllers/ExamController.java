package pl.archala.testme.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/exams", produces = "application/json")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository examRepo;

    private final QuestionRepository questionRepo;

    public ExamController(ExamRepository examRepo, QuestionRepository questionRepo) {
        this.examRepo = examRepo;
        this.questionRepo = questionRepo;
    }
    @GetMapping
    public ResponseEntity<List<Exam>> getExams() {
        if (questionRepo.count() == 0) {
            Question q1 = new Question("Co to jest encja?", Arrays.asList("Zwierzę", "Miejscowość", "Obiekt w bazie danych", "Budynek"));
            Question q2 = new Question("Co to jest obiekt?", Arrays.asList("Owca", "Instancja klasy", "Narzędzie", "Kolor"));
            Question q3 = new Question("Co to jest kompilator?", Arrays.asList("Program", "Rodzaj roweru", "Zawód", "Danie"));
            List<Question> questions = new ArrayList<>(Arrays.asList(q1, q2, q3));
            questionRepo.saveAll(questions);
        }
        if (examRepo.count() == 0) {
            examRepo.save(new Exam("Znajomość języka Java",
                    questionRepo.count(),
                    "Easy",
                    (List<Question>) questionRepo.findAll()));
        }
        List<Exam> exams = (List<Exam>) examRepo.findAll();
        ResponseEntity<List<Exam>> response = new ResponseEntity<>(exams, HttpStatus.OK);
        return response;
    }
    @GetMapping("/exam/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") Long id) {
        Optional<Exam> exam = examRepo.findById(id);
        if (exam.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        ResponseEntity<Exam> response = new ResponseEntity<>(exam.get(), HttpStatus.OK);
        return response;
    }
}
