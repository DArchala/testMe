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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        assertDoesNotThrow(() -> answerService.putAnswer(new Answer()));
    }

    @Test
    void putAnswerShouldReturnTrueIfAnswerIsFilledCorrectly() {
        //given
        Answer answer = new Answer("sampleContent", true);
        answer.setId(1L);

        //when
        when(answerRepo.findById(answer.getId())).thenReturn(Optional.of(answer));

        //then
        assertDoesNotThrow(() -> answerService.putAnswer(answer));
    }

    @Test
    void findAnswerByIdShouldThrowExceptionIfAnswerDoesNotExist() {
        when(answerRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> answerService.findAnswerById(anyLong()));
    }

}