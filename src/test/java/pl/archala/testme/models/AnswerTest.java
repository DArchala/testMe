package pl.archala.testme.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerTest {

    @Test
    void shouldReturnFalseIfContentIsEmptyOrBlank() {
        Answer answer = new Answer("   ", true);
        assertFalse(answer.areFieldsCorrect());
    }

    @Test
    void shouldReturTrueIfAllFieldsAreCorrect() {
        Answer answer = new Answer("Przykład", true);
        assertTrue(answer.areFieldsCorrect());
    }

}