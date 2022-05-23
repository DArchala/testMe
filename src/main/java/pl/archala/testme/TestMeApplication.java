package pl.archala.testme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.enums.ExamDifficultyLevel;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.entity.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.entity.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repository.AnswerRepository;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.QuestionRepository;

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

            List<Answer> singles = new ArrayList<>(Arrays.asList(
                    new Answer("Database", true),
                    new Answer("DuaLipa"),
                    new Answer("DiagramBeta")));

            answerRepo.saveAll(singles);
            var singleChoiceQuestion = new SingleChoiceQuestion("Co to jest DB?", singles);
            questionRepo.save(singleChoiceQuestion);

            Answer single4 = new Answer("Tak", true);
            Answer single5 = new Answer("Nie");
            answerRepo.save(single4);
            answerRepo.save(single5);
            List<Answer> singleChoiceQuestionAnswers2 = new ArrayList<>(Arrays.asList(single4, single5));
            var singleChoiceQuestion2 = new SingleChoiceQuestion("Czy Java jest językiem obiektowym?", singleChoiceQuestionAnswers2);
            questionRepo.save(singleChoiceQuestion2);

            List<Answer> multiples = new ArrayList<>(Arrays.asList(
                    new Answer("class", true),
                    new Answer("continue", true),
                    new Answer("jump"),
                    new Answer("math"),
                    new Answer("money"),
                    new Answer("compiler")));
            answerRepo.saveAll(multiples);
            var multipleChoiceQuestion = new MultipleChoiceQuestion("Wybierz słowa kluczowe:", multiples);
            questionRepo.save(multipleChoiceQuestion);

            List<Answer> multiples2 = new ArrayList<>(Arrays.asList(
                    new Answer("Join"),
                    new Answer("Character", true),
                    new Answer("Exception"),
                    new Answer("Integer", true),
                    new Answer("Class"),
                    new Answer("System")));
            answerRepo.saveAll(multiples2);
            var multipleChoiceQuestion2 = new MultipleChoiceQuestion("Zaznacz klasy opakowujące:", multiples2);
            questionRepo.save(multipleChoiceQuestion2);

            Answer short1 = new Answer("float", true);
            Answer short2 = new Answer("double", true);
            answerRepo.save(short1);
            answerRepo.save(short2);
            List<Answer> shortAnswerQuestionAnswers = new ArrayList<>(Arrays.asList(short1, short2));
            var shortAnswerQuestion = new ShortAnswerQuestion("Wymień typy zmiennych przechowujących wartości liczb zmiennoprzecinkowych.", shortAnswerQuestionAnswers, "");
            questionRepo.save(shortAnswerQuestion);

            List<Answer> shorts2 = new ArrayList<>(Arrays.asList(
                    new Answer("byte", true),
                    new Answer("short", true),
                    new Answer("int", true),
                    new Answer("long", true)));
            answerRepo.saveAll(shorts2);
            var shortAnswerQuestion2 = new ShortAnswerQuestion("Wymień typy zmiennych przechowujących wartości liczb całkowitych.", shorts2, "");
            questionRepo.save(shortAnswerQuestion2);

            List<Question> questions = new ArrayList<>(Arrays.asList(
                    singleChoiceQuestion,
                    singleChoiceQuestion2,
                    multipleChoiceQuestion,
                    multipleChoiceQuestion2,
                    shortAnswerQuestion,
                    shortAnswerQuestion2));

            Exam userExam = new Exam(questions, "sampleExamName", ExamDifficultyLevel.MEDIUM, 3600);
            examRepo.save(userExam);
        };
    }

}
