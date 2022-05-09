package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;

import java.util.ArrayList;
import java.util.List;

class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private ExamService examService;

    @Mock
    private AnswerService answerService;

    @Test
    void shouldReturnPointsForSingleChoiceQuestion() {
        //given
        Answer a1 = new Answer();
        List<Answer> answerList = new ArrayList<>();


        var singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion.setId(1L);
        singleChoiceQuestion.setContent("Co to jest Gothic?");
        singleChoiceQuestion.setAnswers();

        //when
        questionService.countQuestionPoints();

    }
}