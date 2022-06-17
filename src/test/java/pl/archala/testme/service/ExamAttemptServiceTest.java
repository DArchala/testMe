package pl.archala.testme.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class ExamAttemptServiceTest {

    @InjectMocks
    private ExamAttemptService examAttemptService;

    @Mock
    private UserRepository userRepo;

    @Test
    void findExamAttemptsByUsernameShouldThrowExceptionIfUserDoesNotExist() {
        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> examAttemptService.findExamAttemptsByUsername("username"));
    }

    @Test
    void findExamAttemptsByUsernameShouldReturnUserExamAttemptsList() {
        //given
        List<ExamAttempt> examAttemptList = new ArrayList<>(List.of(new ExamAttempt()));
        User user = new User();
        user.setExamAttempts(examAttemptList);

        //when
        when(userRepo.findByUsername("username")).thenReturn(Optional.of(user));

        //then
        assertEquals(examAttemptList, examAttemptService.findExamAttemptsByUsername("username"));
    }

}