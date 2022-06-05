package pl.archala.testme.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.archala.testme.enums.ExamDifficultyLevel;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ExamAttemptTest {

    private ExamAttempt examAttempt1;
    private ExamAttempt examAttempt2;

    @BeforeEach
    void setUp() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(10);
        examAttempt1 = new ExamAttempt(null, "examname", 1, 1, 1, 1, start, end, ExamDifficultyLevel.MEDIUM);
        examAttempt2 = new ExamAttempt(null, "examname", 1, 1, 1, 1, start, end, ExamDifficultyLevel.MEDIUM);
    }

    @Test
    void hashCodeShouldBeEqualIfObjectsAreSame() {
        assertEquals(examAttempt1.hashCode(), examAttempt2.hashCode());
    }

    @Test
    void hashCodeShouldNotBeEqualIfObjectsAreDifferent() {
        examAttempt1.setExamName("exam1");
        examAttempt2.setExamName("exam2");
        assertNotEquals(examAttempt1.hashCode(), examAttempt2.hashCode());
    }

    @Test
    void toStringResultsShouldBeEqualIfObjectsAreSame() {
        assertEquals(examAttempt1.toString(), examAttempt2.toString());

    }

}