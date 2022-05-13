package pl.archala.testme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.ExamDifficultyLevel;
import pl.archala.testme.models.Question;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;
import pl.archala.testme.repositories.ShortAnswerQuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMeApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ExamRepository examRepo,
                                      AnswerRepository answerRepo,
                                      QuestionRepository questionRepo) {
        return (args) -> {

            Answer single1 = new Answer("Database", true);
            Answer single2 = new Answer("DuaLipa");
            Answer single3 = new Answer("DiagramBeta");
            answerRepo.save(single1);
            answerRepo.save(single2);
            answerRepo.save(single3);
            List<Answer> singleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(single1, single2, single3));
            var singleChoiceQuestion = new SingleChoiceQuestion("Co to jest DB?", singleChoiceQuestionAnswers);
            questionRepo.save(singleChoiceQuestion);

            Answer multi1 = new Answer("class", true);
            Answer multi2 = new Answer("continue", true);
            Answer multi3 = new Answer("jump");
            Answer multi4 = new Answer("math");
            Answer multi5 = new Answer("money");
            Answer multi6 = new Answer("compiler");
            answerRepo.save(multi1);
            answerRepo.save(multi2);
            answerRepo.save(multi3);
            answerRepo.save(multi4);
            answerRepo.save(multi5);
            answerRepo.save(multi6);
            List<Answer> multipleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(multi1, multi2, multi3, multi4, multi5, multi6));
            var multipleChoiceQuestion = new MultipleChoiceQuestion("Wybierz słowa kluczowe:", multipleChoiceQuestionAnswers);
            questionRepo.save(multipleChoiceQuestion);

            Answer short1 = new Answer("float", true);
            Answer short2 = new Answer("double", true);
            answerRepo.save(short1);
            answerRepo.save(short2);
            List<Answer> shortAnswerQuestionAnswers = new ArrayList<>(Arrays.asList(short1, short2));
            var shortAnswerQuestion = new ShortAnswerQuestion("Wymień 2 typy zmiennych przechowujących liczby zmiennoprzecinkowe.", shortAnswerQuestionAnswers, "");
            questionRepo.save(shortAnswerQuestion);

            List<Question> questions = new ArrayList<>(Arrays.asList(singleChoiceQuestion, multipleChoiceQuestion, shortAnswerQuestion));

            Exam userExam = new Exam(questions, "sampleExamName", ExamDifficultyLevel.MEDIUM, 3600);
            examRepo.save(userExam);
        };
    }

}
