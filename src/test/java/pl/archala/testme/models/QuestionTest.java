package pl.archala.testme.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void wrongQuestionAnswerShouldReturnFalse() {
        //given
        Question questionToCheck = new Question("Co ma Ala?",
                Arrays.asList(new Answer(1, "Kota"),
                        new Answer(2, "Psa"),
                        new Answer(3, "Kompilator", true),
                        new Answer(4, "Autyzm")));
        Question questionTemplate = new Question("Co ma Ala?",
                Arrays.asList(new Answer(1, "Kota", true),
                        new Answer(2, "Psa"),
                        new Answer(3, "Kompilator"),
                        new Answer(4, "Autyzm")));
        //when
        //then
        assertFalse(questionToCheck.isFilledCorrectly(questionTemplate));
    }

    @Test
    void correctQuestionAnswerShouldReturnTrue() {
        //given
        Question questionToCheck = new Question("Co ma Ala?",
                Arrays.asList(new Answer(1, "Kota", true),
                        new Answer(2, "Psa"),
                        new Answer(3, "Kompilator"),
                        new Answer(4, "Autyzm")));
        Question questionTemplate = new Question("Co ma Ala?",
                Arrays.asList(new Answer(1, "Kota", true),
                        new Answer(2, "Psa"),
                        new Answer(3, "Kompilator"),
                        new Answer(4, "Autyzm")));
        //when
        //then
        assertTrue(questionToCheck.isFilledCorrectly(questionTemplate));
    }

    @Test
    void shouldThrowExceptionIfQuestionAnswersNotContainWantedId() {
        //given
        Question request = new Question("Question content",
                Arrays.asList(new Answer(1, "Answer1", true)));
        Question template = new Question("Question content",
                Arrays.asList(new Answer(5, "Answer1", true)));
        //then
        assertThrows(NoSuchElementException.class, () -> request.isFilledCorrectly(template));

    }
}