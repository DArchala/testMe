package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.repository.AnswerRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepo;

    @Test
    void putAnswerShouldThrowExceptionIfAnswerIsNew() {
        assertThrows(NoSuchElementException.class, () -> answerService.putAnswer(new Answer()));
    }

    @Test
    void putAnswerShouldThrowExceptionIfAnswerRepositoryCannotFindAnswerById() {
        Answer answer = new Answer();
        answer.setId(1L);
        assertThrows(NoSuchElementException.class, () -> answerService.putAnswer(answer));
    }

    @Test
    void putAnswerShouldReturnTrueIfAnswerIsFilledCorrectly() {
        //given
        Answer answer = new Answer("sampleContent", true);
        answer.setId(1L);

        //when
        when(answerRepo.findById(answer.getId())).thenReturn(Optional.of(answer));

        //then
        assertTrue(answerService.putAnswer(answer));
    }

}