package pl.archala.testme.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.entity.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.entity.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.enums.ExamDifficultyLevel;

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
            for (Answer a : q.getAnswers()) {
                assertFalse(a.isCorrectness());
            }
        }
    }

    @Test
    void shouldReturnFalseIfExamQuestionsSizeIsLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> sampleExam.setQuestions(new ArrayList<>(List.of())));

    }

    @Test
    void shouldReturnFalseIfExamNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> sampleExam.setExamName(""));
    }

    @Test
    void shouldReturnFalseIfExamDifficultyLevelIsNull() {
        assertThrows(IllegalArgumentException.class, () -> sampleExam.setDifficultyLevel(null));
    }

    @Test
    void shouldReturnFalseIfExamTimeInSecondsIsLessThan60() {
        assertThrows(IllegalArgumentException.class, () -> sampleExam.setTimeInSeconds(50));
    }

    @Test
    void shouldReturnFalseIfExamTimeInSecondsIsGreaterThan360000() {
        assertThrows(IllegalArgumentException.class, () -> sampleExam.setTimeInSeconds(360_001));
    }

    @Test
    void theSameObjectsShouldBeEqual() {
        Exam exam1 = new Exam(List.of(new SingleChoiceQuestion(), new MultipleChoiceQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 1000);

        Exam exam2 = new Exam(List.of(new SingleChoiceQuestion(), new MultipleChoiceQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 1000);

        assertTrue(exam1.equals(exam2));
        assertTrue(exam2.equals(exam1));
        assertEquals(exam1.hashCode(), exam2.hashCode());

    }

    @Test
    void differentObjectsShouldNotBeEqual() {
        Exam exam1 = new Exam(List.of(new SingleChoiceQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 1000);

        Exam exam2 = new Exam(List.of(new MultipleChoiceQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 1000);

        Exam exam3 = new Exam(List.of(new ShortAnswerQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 1000);

        Exam exam4 = new Exam(List.of(new SingleChoiceQuestion()), "examName", ExamDifficultyLevel.HARD, 1000);

        Exam exam5 = new Exam(List.of(new SingleChoiceQuestion()), "examName", ExamDifficultyLevel.MEDIUM, 500);

        Exam exam6 = new Exam(List.of(new SingleChoiceQuestion()), "examName1", ExamDifficultyLevel.MEDIUM, 1000);

        assertFalse(exam1.equals(exam2));
        assertFalse(exam1.equals(exam3));
        assertFalse(exam1.equals(exam4));
        assertFalse(exam1.equals(exam5));
        assertFalse(exam1.equals(exam6));

        assertNotEquals(exam1.hashCode(), exam2.hashCode());
        assertNotEquals(exam1.hashCode(), exam3.hashCode());
        assertNotEquals(exam1.hashCode(), exam4.hashCode());
        assertNotEquals(exam1.hashCode(), exam5.hashCode());
        assertNotEquals(exam1.hashCode(), exam6.hashCode());

    }
}