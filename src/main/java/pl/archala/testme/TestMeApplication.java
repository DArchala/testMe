package pl.archala.testme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.ExamDifficultyLevel;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.entity.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.entity.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repository.AnswerRepository;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.QuestionRepository;
import pl.archala.testme.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMeApplication.class, args);
    }

}
