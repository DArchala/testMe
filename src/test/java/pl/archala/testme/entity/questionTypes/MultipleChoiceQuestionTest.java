package pl.archala.testme.entity.questionTypes;

import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceQuestionTest {

    @Test
    void markingOneCorrectAnswerShouldReturnOnePoint() {
        //given
        var fromUser = getQuestionWithTheseAnswers(true, false, false);
        var correct = getQuestionWithTheseAnswers(true, false, false);

        //then
        assertEquals(1, fromUser.countPoints(correct));

    }

    @Test
    void markingTwoCorrectAnswerShouldReturnTwoPoint() {
        //given
        var fromUser = getQuestionWithTheseAnswers(true, true, false);
        var correct = getQuestionWithTheseAnswers(true, true, false);

        //then
        assertEquals(2, fromUser.countPoints(correct));

    }

    @Test
    void markingOneWrongAndOneCorrectAnswerShouldReturnZeroPoints() {
        //given
        var fromUser = getQuestionWithTheseAnswers(true, true, false);
        var correct = getQuestionWithTheseAnswers(true, false, false);

        //then
        assertEquals(0, fromUser.countPoints(correct));

    }

    @Test
    void markingOneWrongAndTwoCorrectAnswerShouldReturnOnePoint() {
        //given
        var fromUser = getQuestionWithTheseAnswers(true, true, true);
        var correct = getQuestionWithTheseAnswers(true, true, false);

        //then
        assertEquals(1, fromUser.countPoints(correct));

    }

    private MultipleChoiceQuestion getQuestionWithTheseAnswers(boolean b, boolean b1, boolean b2) {
        Answer templateAnswer4 = new Answer("Database", b);
        Answer templateAnswer5 = new Answer("Drum&Bass", b1);
        Answer templateAnswer6 = new Answer("DigitalBorneo", b2);
        templateAnswer4.setId(1L);
        templateAnswer5.setId(2L);
        templateAnswer6.setId(3L);
        return new MultipleChoiceQuestion("What is DB?",
                Arrays.asList(templateAnswer4, templateAnswer5, templateAnswer6));
    }

    @Test
    void multipleChoiceQuestionShouldThrowExceptionIfNumberOfAnswersIsLessThanTwo() {
        assertThrows(IllegalArgumentException.class, () -> new MultipleChoiceQuestion("content", Arrays.asList()));
    }

    @Test
    void multipleChoiceQuestionShouldThrowExceptionIfNotContainAnyCorrectAnswer() {
        assertThrows(IllegalArgumentException.class, () -> new MultipleChoiceQuestion("content", Arrays.asList(
                new Answer("answ1", false),
                new Answer("answ2", false)
        )));
    }

    @Test
    void theSameObjectsShouldBeEqual() {
        var question1 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question1.setId(1L);

        var question2 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question2.setId(1L);

        assertTrue(question1.equals(question2));
        assertTrue(question2.equals(question1));

    }

    @Test
    void differentObjectsShouldNotBeEqual() {
        var question1 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question1.setId(1L);

        var question2 = new SingleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question2.setId(2L);

        var question3 = new MultipleChoiceQuestion("content", List.of(
                new Answer(), new Answer("answer", true)));
        question1.setId(1L);

        assertFalse(question1.equals(question2));
        assertFalse(question1.equals(question3));
        assertFalse(question2.equals(question1));
        assertFalse(question2.equals(question3));
        assertFalse(question3.equals(question1));
        assertFalse(question3.equals(question2));
    }

    @Test
    void sameObjectsShouldHaveSameHashCodes1() {
        var question1 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question1.setId(1L);

        var question2 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question2.setId(1L);

        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    void differentObjectsShouldHaveDifferentHashCodes1() {
        // different answers size
        var question1 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer(), new Answer()));
        question1.setId(1L);

        // different question content
        var question2 = new MultipleChoiceQuestion("content2", List.of(
                new Answer("answer", true), new Answer()));
        question2.setId(1L);

        // different id
        var question3 = new MultipleChoiceQuestion("content", List.of(
                new Answer("answer", true), new Answer()));
        question2.setId(2L);

        assertNotEquals(question1.hashCode(), question2.hashCode());
        assertNotEquals(question1.hashCode(), question3.hashCode());
        assertNotEquals(question2.hashCode(), question3.hashCode());

    }
}