package pl.archala.testme.entity;

import org.junit.jupiter.api.Test;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionTest {

    @Test
    void setContentShouldThrowExceptionIfContentIsEmpty() {
        var s = new SingleChoiceQuestion();
        assertThrows(IllegalArgumentException.class, () -> s.setContent("  "));
    }
}