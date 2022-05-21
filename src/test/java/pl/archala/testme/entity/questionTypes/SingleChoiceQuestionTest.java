package pl.archala.testme.entity.questionTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleChoiceQuestionTest {

    private SingleChoiceQuestion fromUser;
    private SingleChoiceQuestion template;
    private final List<Answer> answersFromUser = new ArrayList<>();
    private final List<Answer> answersTemplate = new ArrayList<>();
    private int points;

    @BeforeEach
    void setUp() {
        Answer a1 = new Answer("answCont1", false);
        a1.setId(1L);
        Answer a2 = new Answer("answCont2", false);
        a2.setId(2L);
        Answer a3 = new Answer("answCont3", false);
        a3.setId(3L);
        Answer a4 = new Answer("answCont4", false);
        a4.setId(4L);

        answersFromUser.addAll(Arrays.asList(a1, a2, a3, a4));

        Answer a5 = new Answer("answCont1", false);
        a5.setId(1L);
        Answer a6 = new Answer("answCont2", false);
        a6.setId(2L);
        Answer a7 = new Answer("answCont3", false);
        a7.setId(3L);
        Answer a8 = new Answer("answCont4", false);
        a8.setId(4L);

        answersTemplate.addAll(Arrays.asList(a5, a6, a7, a8));

    }

    @Test
    void correctAnswerShouldReturnOnePoint() {
        //given
        Answer userAns = new Answer("answCont2", true);
        userAns.setId(2L);
        Answer templ = new Answer("answCont2", true);
        templ.setId(2L);

        //when
        answersFromUser.set(1, userAns);
        answersTemplate.set(1, templ);

        fromUser = new SingleChoiceQuestion("questCont", answersFromUser);
        template = new SingleChoiceQuestion("questCont", answersTemplate);

        points = fromUser.countPoints(template);

        //then
        assertEquals(1, points);

    }

    @Test
    void wrongAnswerShouldReturnZeroPoints() {
        //given
        Answer userAns = new Answer("answCont2", true);
        userAns.setId(1L);
        Answer templ = new Answer("answCont2", true);
        templ.setId(2L);

        //when
        answersFromUser.set(0, userAns);
        answersTemplate.set(1, templ);

        fromUser = new SingleChoiceQuestion("questCont", answersFromUser);
        template = new SingleChoiceQuestion("questCont", answersTemplate);

        points = fromUser.countPoints(template);

        //then
        assertEquals(0, points);

    }

    @Test
    void noneUserAnswerShouldReturnZeroPoints() {
        //given
        Answer templ = new Answer("answCont2", true);
        templ.setId(2L);

        //when
        answersTemplate.set(1, templ);

        fromUser = new SingleChoiceQuestion("questCont", answersFromUser);
        template = new SingleChoiceQuestion("questCont", answersTemplate);

        points = fromUser.countPoints(template);

        //then
        assertEquals(0, points);
    }

    @Test
    void setAnswerShouldThrowExceptionIfAnswersSizeIsLessThanTwo() {
        var s = new SingleChoiceQuestion();
        assertThrows(IllegalArgumentException.class, () -> s.setAnswers(List.of()));
        assertThrows(IllegalArgumentException.class, () -> s.setAnswers(List.of(new Answer())));
    }

}