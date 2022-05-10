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
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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
        //given
        Answer a1 = new Answer();
        a1.setId(1L);
        a1.setContent("Pies");
        a1.setCorrectness(true);

        Answer a2 = new Answer();
        a2.setId(2L);
        a2.setContent("Kot");
        a2.setCorrectness(true);

        Answer a3 = new Answer();
        a3.setId(3L);
        a3.setContent("Ser");
        a3.setCorrectness(false);
        List<Answer> answersFromUser = new ArrayList<>(Arrays.asList(a1, a2, a3));

        var multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setId(1L);
        multipleChoiceQuestion.setContent("Wybierz zwierzęta:");
        multipleChoiceQuestion.setAnswers(answersFromUser);

        var multipleChoiceQuestionTemplate = new MultipleChoiceQuestion();
        multipleChoiceQuestionTemplate.setId(1L);
        multipleChoiceQuestionTemplate.setContent("Wybierz zwierzęta:");
        multipleChoiceQuestionTemplate.setAnswers(answersFromUser);

        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(multipleChoiceQuestionTemplate));

        //then
        assertEquals(2, questionService.countQuestionPoints(multipleChoiceQuestion));

    }
}