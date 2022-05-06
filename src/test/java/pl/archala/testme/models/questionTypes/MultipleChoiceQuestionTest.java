package pl.archala.testme.models.questionTypes;

import org.junit.jupiter.api.Test;
import pl.archala.testme.models.Answer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultipleChoiceQuestionTest {

    @Test
    void markingOneCorrectAnswerShouldReturnOnePoint() {

        //given
        var fromUser = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", false),
                        new Answer(3L, "answContent3", false)
                ));

        var template = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", false),
                        new Answer(3L, "answContent3", true)
                ));

        //when
        int points = fromUser.countQuestionPoints(template);

        //then
        assertEquals(points, 1);

    }

    @Test
    void markingTwoCorrectAnswerShouldReturnTwoPoint() {
        //given
        var fromUser = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", false),
                        new Answer(3L, "answContent3", true)
                ));

        var template = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", false),
                        new Answer(3L, "answContent3", true)
                ));

        //when
        int points = fromUser.countQuestionPoints(template);

        //then
        assertEquals(points, 2);
    }

    @Test
    void markingOneWrongAnswerShouldReturnZeroPoints() {
        //given
        var fromUser = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", true),
                        new Answer(3L, "answContent3", false)
                ));

        var template = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", false),
                        new Answer(2L, "answContent2", false),
                        new Answer(3L, "answContent3", true)
                ));

        //when
        int points = fromUser.countQuestionPoints(template);

        //then
        assertEquals(points, 0);
    }

    @Test
    void markingOneCorrectAndOneWrongAnswerShouldReturnZeroPoints() {
        //given
        var fromUser = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", true),
                        new Answer(2L, "answContent2", true),
                        new Answer(3L, "answContent3", false)
                ));

        var template = new MultipleChoiceQuestion("questContent",
                Arrays.asList(
                        new Answer(1L, "answContent1", false),
                        new Answer(2L, "answContent2", true),
                        new Answer(3L, "answContent3", false)
                ));

        //when
        int points = fromUser.countQuestionPoints(template);

        //then
        assertEquals(points, 0);
    }

    @Test
    void multipleChoiceQuestionShouldThrowExceptionIfNumberOfAnswersIsLessThanTwo() {
        assertThrows(IllegalArgumentException.class, () -> new MultipleChoiceQuestion("content", Arrays.asList()));
    }

    @Test
    void multipleChoiceQuestionShouldThrowExceptionIfAllAnswersAreCorrect() {
        assertThrows(IllegalArgumentException.class, () -> new MultipleChoiceQuestion("content", Arrays.asList(
                new Answer("answ1", true),
                new Answer("answ2", true)
        )));
    }

    @Test
    void multipleChoiceQuestionShouldThrowExceptionIfNotContainAnyCorrectAnswer() {
        assertThrows(IllegalArgumentException.class, () -> new MultipleChoiceQuestion("content", Arrays.asList(
                new Answer("answ1", false),
                new Answer("answ2", false)
        )));
    }


}