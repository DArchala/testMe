package pl.archala.testme.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    private Exam sampleExam;

    @BeforeEach
    void setUp() {
        sampleExam = new Exam();
        sampleExam.setId(1L);
        sampleExam.setExamName("SampleExamName");
        sampleExam.setQuestions(new ArrayList<>(List.of(new SingleChoiceQuestion())));
        sampleExam.setTimeInSeconds(60);
        sampleExam.setDifficultyLevel(ExamDifficultyLevel.MEDIUM);
    }

    @Test
    void allAnswersCorrectnessShouldBeFalseAfterUseToDoIt() {
        //given
        List<Question> questions = new ArrayList<>();
        questions.add(new SingleChoiceQuestion("content",
                Arrays.asList(
                        new Answer("cont", true),
                        new Answer("tnoc")
                )));
        questions.add(new SingleChoiceQuestion("content",
                Arrays.asList(
                        new Answer("cont", true),
                        new Answer("tnoc")
                )));

        sampleExam.setQuestions(questions);

        //when
        sampleExam.setAllAnswersFalse();

        //then
        for (Question q : questions) {
            for (Answer a : q.answers) {
                assertFalse(a.isCorrectness());
            }
        }
    }

    @Test
    void shouldReturnFalseIfExamQuestionsSizeIsLessThanOne() {
        sampleExam.setQuestions(new ArrayList<>(List.of()));
        assertFalse(sampleExam.areFieldsCorrect());
    }

    @Test
    void shouldReturnFalseIfExamNameIsEmpty() {
        sampleExam.setExamName("");
        assertFalse(sampleExam.areFieldsCorrect());
    }

    @Test
    void shouldReturnFalseIfExamDifficultyLevelIsNull() {
        sampleExam.setDifficultyLevel(null);
        assertFalse(sampleExam.areFieldsCorrect());
    }

    @Test
    void shouldReturnFalseIfExamTimeInSecondsIsLessThan60() {
        sampleExam.setTimeInSeconds(50);
        assertFalse(sampleExam.areFieldsCorrect());
    }

    @Test
    void shouldReturnFalseIfExamTimeInSecondsIsGreaterThan86400() {
        sampleExam.setTimeInSeconds(90_000);
        assertFalse(sampleExam.areFieldsCorrect());
    }

    @Test
    void shouldReturnTrueIfAllFieldsAreFilledCorrectly() {
        assertTrue(sampleExam.areFieldsCorrect());
    }

}