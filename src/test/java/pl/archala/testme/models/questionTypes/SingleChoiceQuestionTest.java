package pl.archala.testme.models.questionTypes;

import org.junit.jupiter.api.Test;
import pl.archala.testme.models.Answer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleChoiceQuestionTest {

    @Test
    void correctAnswerShouldReturnOnePoint() {
        //given
        var fromUser = new SingleChoiceQuestion(1L, "questCont", Arrays.asList(
                new Answer(1L, "answCont1", false),
                new Answer(2L, "answCont2", true),
                new Answer(3L, "answCont3", false),
                new Answer(4L, "answCont4", false)
        ));

        var template = new SingleChoiceQuestion(1L, "questCont", Arrays.asList(
                new Answer(1L, "answCont1", false),
                new Answer(2L, "answCont2", true),
                new Answer(3L, "answCont3", false),
                new Answer(4L, "answCont4", false)
        ));

        int points = fromUser.countPoints(template);

        assertEquals(points, 1);

    }

    @Test
    void wrongAnswerShouldReturnZeroPoints() {
        //given
        var fromUser = new SingleChoiceQuestion(1L, "questCont", Arrays.asList(
                new Answer(1L, "answCont1", false),
                new Answer(2L, "answCont2", true),
                new Answer(3L, "answCont3", false),
                new Answer(4L, "answCont4", false)
        ));

        var template = new SingleChoiceQuestion(1L, "questCont", Arrays.asList(
                new Answer(1L, "answCont1", false),
                new Answer(2L, "answCont2", false),
                new Answer(3L, "answCont3", true),
                new Answer(4L, "answCont4", false)
        ));

        int points = fromUser.countPoints(template);

        assertEquals(points, 0);

    }

    @Test
    void numberOfCorrectAnswersShouldBeEqualToOne() {
        assertThrows(IllegalArgumentException.class, () -> new SingleChoiceQuestion("questCont", Arrays.asList(
                new Answer("answCont1", true),
                new Answer( "answCont2", true)
        )));
    }


}