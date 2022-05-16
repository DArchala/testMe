package pl.archala.testme.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.ExamDifficultyLevel;
import pl.archala.testme.models.Question;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.service.ExamService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamControllerTest {

    @InjectMocks
    private ExamController examController;

    @Mock
    private ExamRepository examRepo;

    @Mock
    private ExamService examService;

    @BeforeEach
    void setUp() {
        examController = new ExamController(examRepo, examService);
    }

    @Test
    void getExamsShouldReturnResponseEntityWithExamsList() {
        //given
        Exam exam = getSampleExam();

        List<Exam> examsFromDB = new ArrayList<>(List.of(exam));

        ResponseEntity<List<Exam>> assertResponse = new ResponseEntity<>(examsFromDB, HttpStatus.OK);

        //when
        when(examRepo.findAll()).thenReturn(examsFromDB);

        //then
        assertThat(assertResponse, equalTo(examController.getExams()));

    }

    @Test
    void checkExamCorrectnessShouldReturnEqualNumberOfUserPoints() {
        //when
        when(examController.checkExamCorrectness(getSampleExam())).thenReturn(12);

        //then
        assertEquals(12, examController.checkExamCorrectness(getSampleExam()));
    }

    @Test
    void getExamMaxPointsShouldReturn() {
        //when
        when(examController.getExamMaxPoints(1L)).thenReturn(12);

        //then
        assertEquals(12, examController.getExamMaxPoints(1L));
    }

    @Test
    void getExamByIdShouldReturnResponseEntityWithExamIfItExists() {
        //when
        when(examRepo.findById(1L)).thenReturn(Optional.of(getSampleExam()));

        //then
        assertThat(examController.getExamById(1L), equalTo(new ResponseEntity<>(getSampleExam(), HttpStatus.OK)));
    }

    @Test
    void getExamByIdShouldReturnNullAndNotFoundHttpStatusIfExamNotExists() {
        //when
        when(examRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThat(examController.getExamById(1L), equalTo(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    private Exam getSampleExam() {
        {
            Answer single1 = new Answer("Database", true);
            Answer single2 = new Answer("DuaLipa");
            Answer single3 = new Answer("DiagramBeta");
            single1.setId(1L);
            single2.setId(2L);
            single3.setId(3L);
            List<Answer> singleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(single1, single2, single3));
            var singleChoiceQuestion = new SingleChoiceQuestion("Co to jest DB?", singleChoiceQuestionAnswers);
            singleChoiceQuestion.setId(1L);

            Answer single4 = new Answer("Tak", true);
            Answer single5 = new Answer("Nie");
            single4.setId(4L);
            single5.setId(5L);
            List<Answer> singleChoiceQuestionAnswers2 = new ArrayList<>(Arrays.asList(single4, single5));
            var singleChoiceQuestion2 = new SingleChoiceQuestion("Czy Java jest językiem obiektowym?", singleChoiceQuestionAnswers2);
            singleChoiceQuestion2.setId(2L);

            Answer multi1 = new Answer("class", true);
            Answer multi2 = new Answer("continue", true);
            Answer multi3 = new Answer("jump");
            Answer multi4 = new Answer("math");
            Answer multi5 = new Answer("money");
            Answer multi6 = new Answer("compiler");
            multi1.setId(6L);
            multi2.setId(7L);
            multi3.setId(8L);
            multi4.setId(9L);
            multi5.setId(10L);
            multi6.setId(11L);
            List<Answer> multipleChoiceQuestionAnswers = new ArrayList<>(Arrays.asList(multi1, multi2, multi3, multi4, multi5, multi6));
            var multipleChoiceQuestion = new MultipleChoiceQuestion("Wybierz słowa kluczowe:", multipleChoiceQuestionAnswers);
            multipleChoiceQuestion.setId(3L);

            Answer multi7 = new Answer("Join");
            Answer multi8 = new Answer("Character", true);
            Answer multi9 = new Answer("Exception");
            Answer multi10 = new Answer("Integer", true);
            Answer multi11 = new Answer("Class");
            Answer multi12 = new Answer("System");
            multi7.setId(12L);
            multi8.setId(13L);
            multi9.setId(14L);
            multi10.setId(15L);
            multi11.setId(16L);
            multi12.setId(17L);
            List<Answer> multipleChoiceQuestionAnswers2 = new ArrayList<>(Arrays.asList(multi7, multi8, multi9, multi10, multi11, multi12));
            var multipleChoiceQuestion2 = new MultipleChoiceQuestion("Zaznacz klasy opakowujące:", multipleChoiceQuestionAnswers2);
            multipleChoiceQuestion2.setId(4L);

            Answer short1 = new Answer("float", true);
            Answer short2 = new Answer("double", true);
            short1.setId(18L);
            short2.setId(19L);
            List<Answer> shortAnswerQuestionAnswers = new ArrayList<>(Arrays.asList(short1, short2));
            var shortAnswerQuestion = new ShortAnswerQuestion("Wymień typy zmiennych przechowujących wartości liczb zmiennoprzecinkowych.", shortAnswerQuestionAnswers, "");
            shortAnswerQuestion.setId(5L);

            Answer short3 = new Answer("byte", true);
            Answer short4 = new Answer("short", true);
            Answer short5 = new Answer("int", true);
            Answer short6 = new Answer("long", true);
            short3.setId(20L);
            short4.setId(21L);
            short5.setId(22L);
            short6.setId(23L);
            List<Answer> shortAnswerQuestionAnswers2 = new ArrayList<>(Arrays.asList(short3, short4, short5, short6));
            var shortAnswerQuestion2 = new ShortAnswerQuestion("Wymień typy zmiennych przechowujących wartości liczb całkowitych.", shortAnswerQuestionAnswers2, "");
            shortAnswerQuestion2.setId(6L);

            List<Question> questions = new ArrayList<>(Arrays.asList(
                    singleChoiceQuestion,
                    singleChoiceQuestion2,
                    multipleChoiceQuestion,
                    multipleChoiceQuestion2,
                    shortAnswerQuestion,
                    shortAnswerQuestion2));

            Exam userExam = new Exam(questions, "sampleExamName", ExamDifficultyLevel.MEDIUM, 3600);
            userExam.setId(1L);
            return userExam;
        }
    }
}