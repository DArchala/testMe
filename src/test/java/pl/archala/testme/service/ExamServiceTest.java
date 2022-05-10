package pl.archala.testme.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.ExamDifficultyLevel;
import pl.archala.testme.models.Question;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @InjectMocks
    private ExamService examService;

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepo;

    @Mock
    private AnswerRepository answerRepo;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepo);
        examService = new ExamService(questionService);
    }

    @Test
    void countUserExamPointsShouldReturnFivePoints() {
        //given
        //userExam
        Answer single1 = new Answer("Database", true);
        Answer single2 = new Answer("DuaLipa");
        Answer single3 = new Answer("DiagramBeta");
        single1.setId(1L);
        single2.setId(2L);
        single3.setId(3L);
        List<Answer> singleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(single1, single2, single3));
        var singleChoiceQuestion = new SingleChoiceQuestion("Co to jest DB?", singleChoiceQuestionAnswers);
        singleChoiceQuestion.setId(1L);

        Answer multi1 = new Answer("class", true);
        Answer multi2 = new Answer("assert", true);
        Answer multi3 = new Answer("jump");
        multi1.setId(4L);
        multi2.setId(5L);
        multi3.setId(6L);
        List<Answer> multipleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(multi1, multi2, multi3));
        var multipleChoiceQuestion = new MultipleChoiceQuestion("Wybierz słowa kluczowe:", multipleChoiceQuestionAnswers);
        multipleChoiceQuestion.setId(2L);

        Answer short1 = new Answer("float", true);
        Answer short2 = new Answer("double", true);
        short1.setId(7L);
        short2.setId(8L);
        List<Answer> shortAnswerQuestionAnswers = new ArrayList<>(Arrays.asList(short1, short2));
        var shortAnswerQuestion = new ShortAnswerQuestion("Wymień 2 typy zmiennych przechowujących liczby zmiennoprzecinkowe.", shortAnswerQuestionAnswers, "float,double");
        shortAnswerQuestion.setId(3L);

        List<Question> questions = new ArrayList<>(Arrays.asList(singleChoiceQuestion, multipleChoiceQuestion, shortAnswerQuestion));

        Exam userExam = new Exam(questions, "sampleExamName", ExamDifficultyLevel.MEDIUM, 3600);
        userExam.setId(1L);

        //dbExam
        Answer single1DB = new Answer("Database", true);
        Answer single2DB = new Answer("DuaLipa");
        Answer single3DB = new Answer("DiagramBeta");
        single1DB.setId(1L);
        single2DB.setId(2L);
        single3DB.setId(3L);
        List<Answer> singleChoiceQuestionAnswersDB = new ArrayList<>(Arrays.asList(single1DB, single2DB, single3DB));
        var singleChoiceQuestionDB = new SingleChoiceQuestion("Co to jest DB?", singleChoiceQuestionAnswersDB);
        singleChoiceQuestionDB.setId(1L);

        Answer multi1DB = new Answer("class", true);
        Answer multi2DB = new Answer("assert", true);
        Answer multi3DB = new Answer("jump");
        multi1DB.setId(4L);
        multi2DB.setId(5L);
        multi3DB.setId(6L);
        List<Answer> multipleChoiceQuestionAnswersDB = new ArrayList<>(Arrays.asList(multi1DB, multi2DB, multi3DB));
        var multipleChoiceQuestionDB = new MultipleChoiceQuestion("Wybierz słowa kluczowe:", multipleChoiceQuestionAnswersDB);
        multipleChoiceQuestionDB.setId(2L);

        Answer short1DB = new Answer("float", true);
        Answer short2DB = new Answer("double", true);
        short1DB.setId(7L);
        short2DB.setId(8L);
        List<Answer> shortAnswerQuestionAnswersDB = new ArrayList<>(Arrays.asList(short1DB, short2DB));
        var shortAnswerQuestionDB = new ShortAnswerQuestion("Wymień 2 typy zmiennych przechowujących liczby zmiennoprzecinkowe.", shortAnswerQuestionAnswersDB, "");
        shortAnswerQuestionDB.setId(3L);
        List<Question> questionsDB = new ArrayList<>(Arrays.asList(singleChoiceQuestionDB, multipleChoiceQuestionDB, shortAnswerQuestionDB));

        Exam dbExam = new Exam(questionsDB, "sampleExamName", ExamDifficultyLevel.MEDIUM, 3600);
        dbExam.setId(1L);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(singleChoiceQuestionDB));
        when(questionRepo.findById(2L)).thenReturn(Optional.of(multipleChoiceQuestionDB));
        when(questionRepo.findById(3L)).thenReturn(Optional.of(shortAnswerQuestionDB));

/*
        when(answerRepo.findById(1L)).thenReturn(Optional.of(single1DB));
        when(answerRepo.findById(2L)).thenReturn(Optional.of(single2DB));
        when(answerRepo.findById(3L)).thenReturn(Optional.of(single3DB));

        when(answerRepo.findById(4L)).thenReturn(Optional.of(multi1DB));
        when(answerRepo.findById(5L)).thenReturn(Optional.of(multi2DB));
        when(answerRepo.findById(6L)).thenReturn(Optional.of(multi3DB));

        when(answerRepo.findById(7L)).thenReturn(Optional.of(short1DB));
        when(answerRepo.findById(8L)).thenReturn(Optional.of(short2DB));
*/

        //then
        assertEquals(5, examService.countUserExamPoints(userExam));

    }
}