package pl.archala.testme.component;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ExamDateTimeTest {

    @Test
    void toStringShouldBeEqualIfObjectsAreSame() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(10);
        long userExamTime = 100;
        ExamDateTime examDateTime1 = new ExamDateTime(start, end, userExamTime);
        ExamDateTime examDateTime2 = new ExamDateTime(start, end, userExamTime);

        assertEquals(examDateTime1.toString(), examDateTime2.toString());
    }

    @Test
    void hashCodeShouldBeEqualIfObjectsAreSame() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(10);
        long userExamTime = 100;
        ExamDateTime examDateTime1 = new ExamDateTime(start, end, userExamTime);
        ExamDateTime examDateTime2 = new ExamDateTime(start, end, userExamTime);

        assertEquals(examDateTime1.hashCode(), examDateTime2.hashCode());
    }

    @Test
    void hashCodeShouldBeEqualIfObjectsAreNotSame() {
        long userExamTime = 100;
        ExamDateTime examDateTime1 = new ExamDateTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), userExamTime);
        ExamDateTime examDateTime2 = new ExamDateTime(LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), userExamTime);

        assertNotEquals(examDateTime1.hashCode(), examDateTime2.hashCode());
    }

}