package pl.archala.testme.models.questionTypes;

import org.junit.jupiter.api.Test;
import pl.archala.testme.models.Answer;

import java.util.Arrays;

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


}