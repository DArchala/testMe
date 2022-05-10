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
        Answer a1 = new Answer("answContent1", true);
        a1.setId(1L);
        Answer a2 = new Answer("answContent2", false);
        a2.setId(2L);
        Answer a3 = new Answer("answContent3", false);
        a3.setId(3L);
        var fromUser = new MultipleChoiceQuestion("questContent", Arrays.asList(a1, a2, a3));

        Answer a4 = new Answer("answContent1", true);
        a4.setId(1L);
        Answer a5 = new Answer("answContent2", false);
        a5.setId(2L);
        Answer a6 = new Answer("answContent3", false);
        a6.setId(3L);
        var template = new MultipleChoiceQuestion("questContent", Arrays.asList(a4, a5, a6));

        //when
        int points = fromUser.countPoints(template);

        //then
        assertEquals(points, 1);

    }

    @Test
    void markingTwoCorrectAnswerShouldReturnTwoPoint() {
        //given
        Answer a1 = new Answer("answContent1", true);
        a1.setId(1L);
        Answer a2 = new Answer("answContent2", true);
        a2.setId(2L);
        Answer a3 = new Answer("answContent3", false);
        a3.setId(3L);
        var fromUser = new MultipleChoiceQuestion("questContent", Arrays.asList(a1, a2, a3));

        Answer a4 = new Answer("answContent1", true);
        a4.setId(1L);
        Answer a5 = new Answer("answContent2", true);
        a5.setId(2L);
        Answer a6 = new Answer("answContent3", false);
        a6.setId(3L);
        var template = new MultipleChoiceQuestion("questContent", Arrays.asList(a4, a5, a6));

        //when
        int points = fromUser.countPoints(template);

        //then
        assertEquals(points, 2);
    }

    @Test
    void markingOneWrongAnswerShouldReturnZeroPoints() {
        //given
        Answer a1 = new Answer("answContent1", true);
        a1.setId(1L);
        Answer a2 = new Answer("answContent2", true);
        a2.setId(2L);
        Answer a3 = new Answer("answContent3", false);
        a3.setId(3L);
        var fromUser = new MultipleChoiceQuestion("questContent", Arrays.asList(a1, a2, a3));

        Answer a4 = new Answer("answContent1", false);
        a4.setId(1L);
        Answer a5 = new Answer("answContent2", false);
        a5.setId(2L);
        Answer a6 = new Answer("answContent3", true);
        a6.setId(3L);
        var template = new MultipleChoiceQuestion("questContent", Arrays.asList(a4, a5, a6));

        //when
        int points = fromUser.countPoints(template);

        //then
        assertEquals(points, 0);
    }

    @Test
    void markingOneCorrectAndOneWrongAnswerShouldReturnZeroPoints() {
        //given
        Answer a1 = new Answer("answContent1", true);
        a1.setId(1L);
        Answer a2 = new Answer("answContent2", true);
        a2.setId(2L);
        Answer a3 = new Answer("answContent3", false);
        a3.setId(3L);
        var fromUser = new MultipleChoiceQuestion("questContent", Arrays.asList(a1, a2, a3));

        Answer a4 = new Answer("answContent1", true);
        a4.setId(1L);
        Answer a5 = new Answer("answContent2", false);
        a5.setId(2L);
        Answer a6 = new Answer("answContent3", false);
        a6.setId(3L);
        var template = new MultipleChoiceQuestion("questContent", Arrays.asList(a4, a5, a6));

        //when
        int points = fromUser.countPoints(template);

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