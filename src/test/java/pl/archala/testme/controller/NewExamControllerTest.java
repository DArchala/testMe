package pl.archala.testme.controller;

import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.ExamDifficultyLevel;
import pl.archala.testme.service.ExamService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class NewExamControllerTest {

    @InjectMocks
    private NewExamController newExamController;

    @Mock
    private ExamService examService;

    @BeforeEach
    void setUp() {
        newExamController = new NewExamController(examService);
    }

    @Test
    void getNewExamDataShouldReturnExamDifficultyLevelValues() {
        //given
        ExamDifficultyLevel[] values = ExamDifficultyLevel.values();

        ResponseEntity<ExamDifficultyLevel[]> response = new ResponseEntity<>(values, HttpStatus.OK);

        //then
        assertEquals(newExamController.getNewExamData(), response);
    }

    @Test
    void saveNewExamShouldReturnExamTheSameExamAndStatusOKIfItIsCorrect() {
        //given
        Exam exam = new Exam();
        ResponseEntity<Exam> examResponse = new ResponseEntity<>(exam, HttpStatus.OK);

        //when
        when(examService.saveNewExam(exam)).thenReturn(true);

        //then
        assertEquals(newExamController.saveNewExam(exam), examResponse);
    }

    @Test
    void saveNewExamShouldReturnBadRequestStatusIfItIsIncorrect() {
        //given
        Exam exam = new Exam();
        ResponseEntity<Exam> examResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //when
        when(examService.saveNewExam(exam)).thenReturn(false);

        //then
        assertEquals(newExamController.saveNewExam(exam), examResponse);
    }
}