package pl.archala.testme.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void shouldReturnFalseIfContentIsEmptyOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Answer("   ", true));
    }
    @Test
    void shouldReturTrueIfAllFieldsAreCorrect() {
        Answer answer = new Answer("Przykład", true);
        assertTrue(answer.areFieldsCorrect());
    }

}