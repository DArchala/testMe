package pl.archala.testme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestMeApplication {

    private ExamRepository examRepo;
    private AnswerRepository answerRepo;
    private QuestionRepository questionRepo;

    public static void main(String[] args) {
        SpringApplication.run(TestMeApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(ExamRepository examRepo,
                                      AnswerRepository answerRepo,
                                      QuestionRepository questionRepo) {
        return (args) -> {

            // Answers

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
            List<Answer> answers1 = new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12));
            answerRepo.saveAll(answers1);

            var a13 = new Answer(13L, "Firma produkująca płyty CD");
            var a14 = new Answer(14L, "Rodzaj monitora");
            var a15 = new Answer(15L, "Framework do realizacji warstwy danych", true);
            var a16 = new Answer(16L, "Nie ma takiego czegoś");

            var a17 = new Answer(17L, "@OneToMany");
            var a18 = new Answer(18L, "@OneToOne", true);
            var a19 = new Answer(19L, "@Entity");
            var a20 = new Answer(20L, "@ManyToMany");

            var a21 = new Answer(21L, "@Id", true);
            var a22 = new Answer(22L, "@Table");
            var a23 = new Answer(23L, "@GeneratedValue");
            var a24 = new Answer(24L, "@AllArgsConstructor");
            List<Answer> answers2 = new ArrayList<>(Arrays.asList(a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24));
            answerRepo.saveAll(answers2);

            // Questions

            Question q1 = new Question("Co to jest encja?",
                    Arrays.asList(
                            answers1.get(0),
                            answers1.get(1),
                            answers1.get(2),
                            answers1.get(3)));
            Question q2 = new Question("Co to jest obiekt?",
                    Arrays.asList(
                            answers1.get(4),
                            answers1.get(5),
                            answers1.get(6),
                            answers1.get(7)));
            Question q3 = new Question("Co to jest kompilator?",
                    Arrays.asList(
                            answers1.get(8),
                            answers1.get(9),
                            answers1.get(10),
                            answers1.get(11)));
            List<Question> questions1 = new ArrayList<>(Arrays.asList(q1, q2, q3));
            questionRepo.saveAll(questions1);

            Question q4 = new Question("Co to jest hibernate?",
                    Arrays.asList(
                            answers2.get(0),
                            answers2.get(1),
                            answers2.get(2),
                            answers2.get(3)));
            Question q5 = new Question("Jaka adnotacja przypisuje obiekty jeden do jeden?",
                    Arrays.asList(
                            answers2.get(4),
                            answers2.get(5),
                            answers2.get(6),
                            answers2.get(7)));
            Question q6 = new Question("Jaka adnotacja jest wymagana po dodaniu @Entity do klasy java?",
                    Arrays.asList(
                            answers2.get(8),
                            answers2.get(9),
                            answers2.get(10),
                            answers2.get(11)));
            List<Question> questions2 = new ArrayList<>(Arrays.asList(q4, q5, q6));
            questionRepo.saveAll(questions2);

            // Exams

            Exam exam1 = new Exam("Java knowledge",
                    3,
                    "Easy",
                    questions1);
            exam1.setTimeInSeconds(1800);

            Exam exam2 = new Exam("Hibernate knowledge",
                    3,
                    "Medium",
                    questions2);

            examRepo.saveAll(Arrays.asList(exam1, exam2));
        };
    }

}
