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
import pl.archala.testme.service.ExamService;

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
    private final ExamService examService;
    private final QuestionRepository questionRepo;
    private final AnswerRepository answerRepo;

    public ExamController(ExamRepository examRepo, ExamService examService, QuestionRepository questionRepo, AnswerRepository answerRepo) {
        this.examRepo = examRepo;
        this.examService = examService;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getExams() {
        if (answerRepo.count() == 0) {
            var a1 = new Answer(1L, "Zwierzę", false);
            var a2 = new Answer(2L, "Miejscowość");
            var a3 = new Answer(3L, "Obiekt w bazie danych", true);
            var a4 = new Answer(4L, "Budynek");

            var a5 = new Answer(5L, "Owca");
            var a6 = new Answer(6L, "Instancja klasy", true);
            var a7 = new Answer(7L, "Narzędzie");
            var a8 = new Answer(8L, "Kolor");

            var a9 = new Answer(9L, "Program", true);
            var a10 = new Answer(10L, "Rodzaj roweru");
            var a11 = new Answer(11L, "Zawód");
            var a12 = new Answer(12L, "Danie");

            List<Answer> answers = new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12));
            answerRepo.saveAll(answers);
        }
        if (questionRepo.count() == 0) {
            Question q1 = new Question("Co to jest encja?",
                    Arrays.asList(
                            answerRepo.findById(1L).get(),
                            answerRepo.findById(2L).get(),
                            answerRepo.findById(3L).get(),
                            answerRepo.findById(4L).get()));
            Question q2 = new Question("Co to jest obiekt?",
                    Arrays.asList(
                            answerRepo.findById(5L).get(),
                            answerRepo.findById(6L).get(),
                            answerRepo.findById(7L).get(),
                            answerRepo.findById(8L).get()));
            Question q3 = new Question("Co to jest kompilator?",
                    Arrays.asList(
                            answerRepo.findById(9L).get(),
                            answerRepo.findById(10L).get(),
                            answerRepo.findById(11L).get(),
                            answerRepo.findById(12L).get()));
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
