package pl.archala.testme.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Questionable;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepo;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepo);
    }

    @Test
    void shouldReturnTwoPointsForMultipleChoiceQuestion() {
        List<Answer> answersFromUser = getSampleAnswers(true, true, false);

        var multipleChoiceQuestion = getSampleMultipleChoiceQuestion(answersFromUser);
        var multipleChoiceQuestionTemplate = getSampleMultipleChoiceQuestion(answersFromUser);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(multipleChoiceQuestionTemplate));

        //then
        assertEquals(2, questionService.countQuestionPoints(multipleChoiceQuestion));

    }

    @Test
    void shouldReturnOnePointForMultipleChoiceQuestion() {
        //given
        List<Answer> answersFromUser = getSampleAnswers(true, false, false);
        List<Answer> answersTemplate = getSampleAnswers(true, true, false);

        var multipleChoiceQuestion = getSampleMultipleChoiceQuestion(answersFromUser);
        var multipleChoiceQuestionTemplate = getSampleMultipleChoiceQuestion(answersTemplate);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(multipleChoiceQuestionTemplate));

        //then
        assertEquals(1, questionService.countQuestionPoints(multipleChoiceQuestion));

    }

    @Test
    void shouldReturnZeroPointsForMultipleChoiceQuestion() {

        //given
        List<Answer> answersFromUser = getSampleAnswers(true, true, false);
        List<Answer> answersTemplate = getSampleAnswers(true, false, false);

        var multipleChoiceQuestion = getSampleMultipleChoiceQuestion(answersFromUser);
        var multipleChoiceQuestionTemplate = getSampleMultipleChoiceQuestion(answersTemplate);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(multipleChoiceQuestionTemplate));

        //then
        assertEquals(0, questionService.countQuestionPoints(multipleChoiceQuestion));

    }

    private MultipleChoiceQuestion getSampleMultipleChoiceQuestion(List<Answer> answers) {
        var multipleChoiceQuestionTemplate = new MultipleChoiceQuestion();
        multipleChoiceQuestionTemplate.setId(1L);
        multipleChoiceQuestionTemplate.setContent("Wybierz zwierzęta:");
        multipleChoiceQuestionTemplate.setAnswers(answers);
        return multipleChoiceQuestionTemplate;
    }

    private List<Answer> getSampleAnswers(boolean b, boolean b1, boolean b2) {
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setContent("Pies");
        a1.setCorrectness(b);

        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setContent("Kot");
        a2.setCorrectness(b1);

        Answer a3 = new Answer();
        a3.setId(3L);
        a3.setContent("Ser");
        a3.setCorrectness(b2);
        return new ArrayList<>(Arrays.asList(a1, a2, a3));
    }

    @Test
    void shouldReturnOnePointForSingleChoiceQuestion() {
        //given
        List<Answer> answersFromUser = getSampleAnswers(true, false, false);
        List<Answer> answersTemplate = getSampleAnswers(true, false, false);

        var singleChoiceQuestion = getSampleSingleChoiceQuestion(answersFromUser);
        var singleChoiceQuestionTemplate = getSampleSingleChoiceQuestion(answersTemplate);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(singleChoiceQuestionTemplate));

        //then
        assertEquals(1, questionService.countQuestionPoints(singleChoiceQuestion));

    }

    @Test
    void shouldReturnZeroPointsForSingleChoiceQuestion() {
        //given
        List<Answer> answersFromUser = getSampleAnswers(false, true, false);
        List<Answer> answersTemplate = getSampleAnswers(true, false, false);

        var singleChoiceQuestion = getSampleSingleChoiceQuestion(answersFromUser);
        var singleChoiceQuestionTemplate = getSampleSingleChoiceQuestion(answersTemplate);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(singleChoiceQuestionTemplate));

        //then
        assertEquals(0, questionService.countQuestionPoints(singleChoiceQuestion));

    }

    private SingleChoiceQuestion getSampleSingleChoiceQuestion(List<Answer> answers) {
        var singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion.setId(1L);
        singleChoiceQuestion.setContent("Wybierz zwierzę:");
        singleChoiceQuestion.setAnswers(answers);
        return singleChoiceQuestion;
    }

    @Test
    void shouldReturnOnePointForShortAnswerQuestion() {
        //given

        List<Answer> answersTemplate = getSampleShortAnswers();

        var shortAnswerQuestion = new ShortAnswerQuestion(
                "Dwa najczęstsze domowe zwierzęta:",
                answersTemplate,
                "pies"
        );
        shortAnswerQuestion.setId(1L);

        //when
        // In this variant we don't need question template, so we can pass any ShortAnswerQuestion
        when(questionRepo.findById(1L)).thenReturn(Optional.of(new ShortAnswerQuestion()));

        //then
        assertEquals(1, questionService.countQuestionPoints(shortAnswerQuestion));

    }

    @Test
    void shouldReturnZeroPointsForShortAnswerQuestion() {
        //given
        List<Answer> answersTemplate = getSampleShortAnswers();

        var shortAnswerQuestion = new ShortAnswerQuestion(
                "Dwa najczęstsze domowe zwierzęta:",
                answersTemplate,
                "nie mam pojecia"
        );
        shortAnswerQuestion.setId(1L);

        //when
        // In this variant we don't need question template, so we can pass any ShortAnswerQuestion
        when(questionRepo.findById(1L)).thenReturn(Optional.of(new ShortAnswerQuestion()));

        //then
        assertEquals(0, questionService.countQuestionPoints(shortAnswerQuestion));

    }

    @Test
    void shouldReturnTwoPointsForShortAnswerQuestion() {
        //given
        List<Answer> answersTemplate = getSampleShortAnswers();

        var shortAnswerQuestion = new ShortAnswerQuestion(
                "Dwa najczęstsze domowe zwierzęta:",
                answersTemplate,
                "pies,kot"
        );
        shortAnswerQuestion.setId(1L);

        //when
        // In this variant we don't need question template, so we can pass any ShortAnswerQuestion
        when(questionRepo.findById(1L)).thenReturn(Optional.of(new ShortAnswerQuestion()));

        //then
        assertEquals(2, questionService.countQuestionPoints(shortAnswerQuestion));

    }

    @Test
    void shouldThrowNoSuchElementExceptionIfQuestionTemplateNotExistInDB() {
        //given
        var question = new SingleChoiceQuestion("content", List.of(new Answer(), new Answer()));
        question.setId(1L);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(NoSuchElementException.class, () -> questionService.countQuestionPoints(question));
    }

    private List<Answer> getSampleShortAnswers() {
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setContent("Pies");
        a1.setCorrectness(true);

        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setContent("Kot");
        a2.setCorrectness(true);
        return new ArrayList<>(Arrays.asList(a1, a2));
    }

}