package pl.archala.testme.models;

import org.junit.jupiter.api.Test;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ExamTest {

    @Test
    void allAnswersCorrectnessShouldBeFalseAfterUseToDoIt() {
        //given
        List<Question> questions = new ArrayList<>();
        questions.add(new SingleChoiceQuestion("content",
                Arrays.asList(
                        new Answer("cont", true),
                        new Answer("tnoc")
                )));
        questions.add(new SingleChoiceQuestion("content",
                Arrays.asList(
                        new Answer("cont", true),
                        new Answer("tnoc")
                )));

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setQuestions(questions);
        exam.setExamQuestionsNumber(2);
        exam.setDifficultyLevel(ExamDifficultyLevel.VERY_EASY);
        exam.setTimeInSeconds(60);

        //when
        exam.setAllAnswersFalse();

        //then
        for (Question q : questions) {
            for (Answer a : q.answers) {
                assertFalse(a.isCorrectness());
            }
        }
    }
}