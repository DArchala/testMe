package pl.archala.testme.entity.questionTypes;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.archala.testme.entity.Answer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ShortAnswerQuestionTest {

    @Test
    void creatingShortAnswerQuestionWithoutAnswersShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ShortAnswerQuestion("content", Arrays.asList(), "userAnswer"));
    }

    @Test
    void creatingShortAnswerQuestionWithAnyIncorrectAnswerShouldThrowException() {
        Answer answer = new Answer("content", true);
        Answer answer2 = new Answer("content", false);
        assertThrows(IllegalArgumentException.class,
                () -> new ShortAnswerQuestion("content", Arrays.asList(answer, answer2), "userAnswer"));
    }

    @Test
    void oneCorrectUserAnswersShouldReturnOnePoint() {
        //given
        var answerQuestion = new ShortAnswerQuestion("Aplikacje do podróżowania to:", Arrays.asList(
                new Answer("Blablacar", true),
                new Answer("Uber", true)
        ), "Blablacar");

        //when
        int points = answerQuestion.countPoints();

        //then
        assertEquals(points, 1);
    }

    @Test
    void twoCorrectUserAnswersShouldReturnTwoPoints() {
        //given
        var answerQuestion = new ShortAnswerQuestion("Aplikacje do podróżowania to:", Arrays.asList(
                new Answer("Blablacar", true),
                new Answer("Uber", true)

        ), "Blablacar,Uber");

        //when
        int points = answerQuestion.countPoints();

        //then
        assertEquals(2, points);
    }

    @Test
    void noOneCorrectUserAnswersShouldReturnZeroPoints() {
        //given
        var answerQuestion = new ShortAnswerQuestion("Aplikacje do podróżowania to:", Arrays.asList(
                new Answer("Blablacar", true),
                new Answer("Uber", true)

        ), "Pyszne.pl");

        //when
        int points = answerQuestion.countPoints();

        //then
        assertEquals(points, 0);
    }

    @Test
    void threeUserAnswersWhenOnlyTwoAreCorrectShouldReturnOnePointLess() {
        //given
        var answerQuestion = new ShortAnswerQuestion("Aplikacje do podróżowania to:", Arrays.asList(
                new Answer("Blablacar", true),
                new Answer("Uber", true)

        ), "blablacar, uber, makao");

        //when
        int points = answerQuestion.countPoints();

        //then
        assertEquals(1, points);
    }
}