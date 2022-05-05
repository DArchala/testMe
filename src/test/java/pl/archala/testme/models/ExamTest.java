package pl.archala.testme.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ExamTest {

    @Test
    void allAnswersShouldHaveFalseCorrectnessAfterMethod() {
        //given
        Exam exam = new Exam();
        Question question1 = new Question("question1", Arrays.asList(
                new Answer("answer1", true),
                new Answer("answer2")
        ));
        Question question2 = new Question("question2", Arrays.asList(
                new Answer("answer3"),
                new Answer("answer4", true)
        ));
        exam.setQuestions(Arrays.asList(question1, question2));

        //when
        exam.setAllAnswersFalse();

        //then
        for (Question question : exam.getQuestions()) {
            for (Answer answer : question.getAnswers()) {
                assertFalse(answer.isCorrectness());
            }
        }
    }
}