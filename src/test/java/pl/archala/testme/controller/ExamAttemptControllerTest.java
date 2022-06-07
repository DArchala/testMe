package pl.archala.testme.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ExamAttemptControllerTest {

    @InjectMocks
    private ExamAttemptController examAttemptController;

    @Mock
    private UserRepository userRepo;

    @Mock
    private Principal principal;

    @Test
    void getMyExamAttemptsShouldReturnUserDoesNotExistIfRepoCannotFindUserByUsername() {
        //given
        User user = new User();
        ResponseEntity<?> response = new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);

        //when
        when(userRepo.findByUsername(principal.getName())).thenReturn(Optional.empty());

        //then
        assertEquals(response, examAttemptController.getMyExamAttempts(principal));
    }

    @Test
    void getMyExamAttemptsShouldReturnMyExamAttemptsIfUserExist() {
        //given
        User user = new User();
        List<ExamAttempt> myExamAttempts = new ArrayList<>();
        ResponseEntity<?> response = new ResponseEntity<>(myExamAttempts, HttpStatus.OK);

        //when
        when(userRepo.findByUsername(principal.getName())).thenReturn(Optional.of(user));

        //then
        assertEquals(response, examAttemptController.getMyExamAttempts(principal));
    }
}