package pl.archala.testme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.ExamDifficultyLevel;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.ShortAnswerQuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestMeApplication {

    private ExamRepository examRepo;
    private AnswerRepository answerRepo;
    private ShortAnswerQuestionRepository shortAnsQuestRepo;

    public static void main(String[] args) {
        SpringApplication.run(TestMeApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ExamRepository examRepo,
                                      AnswerRepository answerRepo,
                                      ShortAnswerQuestionRepository shortAnsQuestRepo) {
        return (args) -> {

            Answer a1 = new Answer("Kot", true);
            Answer a2 = new Answer("Pies", true);
            answerRepo.save(a1);
            answerRepo.save(a2);

            var s1 = new ShortAnswerQuestion("Jaki to zwierz?", Arrays.asList(a1, a2), "");
            shortAnsQuestRepo.save(s1);

            Exam exam = new Exam(
                    new ArrayList<>(List.of(s1)),
                    "examtestName",
                    1,
                    ExamDifficultyLevel.EASY,
                    60
            );
            examRepo.save(exam);
        };
    }

}
