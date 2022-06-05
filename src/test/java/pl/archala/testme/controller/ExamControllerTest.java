package pl.archala.testme.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.component.ExamForm;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.entity.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.entity.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.enums.ExamDifficultyLevel;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.service.ExamService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ExamControllerTest {

    @InjectMocks
    private ExamController examController;

    @Mock
    private ExamRepository examRepo;

    @Mock
    private ExamService examService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private Principal principal;

    @Test
    void getExamsShouldReturnResponseEntityWithExamsListIfDBContainAnyExam() {
        //given
        Exam exam = getSampleExam();

        List<Exam> examsFromDB = new ArrayList<>(List.of(exam));

        ResponseEntity<List<Exam>> assertResponse = new ResponseEntity<>(examsFromDB, HttpStatus.OK);

        //when
        when(examService.getAllExams()).thenReturn(examsFromDB);

        //then
        assertThat(assertResponse, equalTo(examController.getExams()));

    }

    @Test
    void getExamsShouldReturnResponseEntityWithNotFoundStatusIfThereIsNotAnyExam() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("No exams found", HttpStatus.NOT_FOUND);

        //when
        when(examService.getAllExams()).thenReturn(List.of());

        //then
        assertEquals(response, examController.getExams());

    }

    @Test
    void getExamMaxPointsShouldReturnMaxPointsForExam() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>(12, HttpStatus.OK);

        //when
        when(examRepo.findById(anyLong())).thenReturn(Optional.of(new Exam()));
        when(examService.getMaxPossibleExamPoints(1L)).thenReturn(12);

        //then
        assertEquals(response, examController.getExamMaxPoints(1L));
    }

    @Test
    void getExamMaxPointsShouldReturnExamNotExistAndNotFoundStatusIfExamDoesNotExist() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Exam does not exist", HttpStatus.NOT_FOUND);

        //when
        when(examRepo.findById(anyLong())).thenReturn(Optional.empty());

        //then
        assertEquals(response, examController.getExamMaxPoints(anyLong()));
    }

    @Test
    void getExamByIdShouldReturnResponseEntityWithExamWithAllAnswersSetToFalseIfItExists() {
        //when
        when(examRepo.findById(1L)).thenReturn(Optional.of(getSampleExam()));

        //then
        assertThat(examController.getExamById(1L), equalTo(new ResponseEntity<>(getSampleExam().setAllAnswersFalse(), HttpStatus.OK)));
    }

    @Test
    void getExamByIdShouldReturnNullAndNotFoundHttpStatusIfExamNotExists() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Exam does not exist", HttpStatus.NOT_FOUND);

        //when
        when(examRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertEquals(response, examController.getExamById(1L));
    }

    @Test
    void getNewExamDataShouldReturnExamDifficultyLevelValues() {
        //given
        ExamDifficultyLevel[] values = ExamDifficultyLevel.values();

        ResponseEntity<ExamDifficultyLevel[]> response = new ResponseEntity<>(values, HttpStatus.OK);

        //then
        assertEquals(examController.getNewExamData(), response);
    }

    @Test
    void saveNewExamShouldReturnExamTheSameExamAndStatusOKIfItIsCorrect() {
        //given
        Exam exam = new Exam();
        ResponseEntity<Exam> examResponse = new ResponseEntity<>(exam, HttpStatus.OK);

        //when
        when(examService.saveNewExam(exam)).thenReturn(true);

        //then
        assertEquals(examController.saveNewExam(exam), examResponse);
    }

    @Test
    void saveNewExamShouldReturnBadRequestStatusIfItIsIncorrect() {
        //given
        Exam exam = new Exam();
        ResponseEntity<?> response = new ResponseEntity<>("Saving exam failed", HttpStatus.BAD_REQUEST);

        //when
        when(examService.saveNewExam(exam)).thenReturn(false);

        //then
        assertEquals(response, examController.saveNewExam(exam));
    }

    @Test
    void getExamToEditByIdShouldReturnResponseEntityWithNotFoundStatusIfExamNotExists() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Exam does not exist", HttpStatus.NOT_FOUND);

        //when
        when(examRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertEquals(response, examController.getExamToEditById(1L));
    }

    @Test
    void getExamToEditByIdShouldReturnResponseEntityWithExamAndOKStatusIfExamExists() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);

        ResponseEntity<Exam> response = new ResponseEntity<>(exam, HttpStatus.OK);

        //when
        when(examRepo.findById(exam.getId())).thenReturn(Optional.of(exam));

        //then
        assertEquals(response, examController.getExamToEditById(exam.getId()));
    }

    @Test
    void putExamShouldReturnResponseEntityWithOKStatusIfExamServiceSaveItSuccessfully() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);

        ResponseEntity<?> response = new ResponseEntity<>("Exam saved", HttpStatus.OK);


        //when
        when(examService.putExam(exam)).thenReturn(true);

        //then
        assertEquals(response, examController.putExam(exam));
    }

    @Test
    void putExamShouldReturnResponseEntityWithNotModifiedStatusIfExamServiceCannotPutExam() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);

        ResponseEntity<Exam> response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        //when
        when(examService.putExam(exam)).thenReturn(false);

        //then
        assertEquals(response, examController.putExam(exam));
    }

    @Test
    void checkExamCorrectnessShouldReturnValueFromExamService() {
        //given
        ExamForm examform = new ExamForm();
        ResponseEntity<?> response = new ResponseEntity<>(1, HttpStatus.OK);

        //when
        when(examService.countUserExamPoints(examform.getExam())).thenReturn(1);

        //then
        assertEquals(response, examController.checkExamCorrectness(examform, principal));
    }

    @Test
    void checkExamCorrectnessShouldReturnUserDoesNotExistInBadWay() {
        //given
        ExamForm examForm = new ExamForm();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);

        //when
        when(examService.saveExamAttemptToUser(examForm, principal.getName())).thenReturn(true);

        //then
        assertEquals(response, examController.checkExamCorrectness(examForm, principal));
    }

    @Test
    void deleteExamShouldReturnStatusOKResponseIfExamServiceDeleteIt() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Exam deleted", HttpStatus.OK);

        //when
        when(examService.deleteExam(anyLong())).thenReturn(true);

        //then
        assertEquals(response, examController.deleteExam(anyLong()));
    }

    @Test
    void deleteExamShouldReturnStatusNotFoundResponseIfExamServiceCannotDeleteIt() {
        //given
        ResponseEntity<?> response = new ResponseEntity<>("Exam does not exist", HttpStatus.NOT_FOUND);

        //when
        when(examService.deleteExam(anyLong())).thenReturn(false);

        //then
        assertEquals(response, examController.deleteExam(anyLong()));
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