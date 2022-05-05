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
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private QuestionRepository questionRepo;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepo, answerRepository, examRepository);
    }
    @Test
    void shouldReturnAsMuchPointsAsCorrectAnswers() {
        //given
        Exam exam = new Exam(1L, null, "EXXAM", 3, "Easy");

        Question question1 = new Question(1L, "What means PC?",
                Arrays.asList(
                        new Answer(1, "Personal Computer", true),
                        new Answer(2, "Polish crysis"),
                        new Answer(3, "Mouse"),
                        new Answer(4, "Materazzi")
                ));
        Question question2 = new Question(2L, "What is HDD?",
                Arrays.asList(
                        new Answer(5, "Hard Disk-Driver", true),
                        new Answer(6, "House Doom Driver"),
                        new Answer(7, "Hello Dummy Damn"),
                        new Answer(8, "Hard River Down")
                ));
        Question question3 = new Question(3L, "What is int?",
                Arrays.asList(
                        new Answer(9, "Variable type", true),
                        new Answer(10, "IDE type"),
                        new Answer(11, "Nothing special"),
                        new Answer(12, "A word")
                ));
        List<Question> questions = Arrays.asList(question1, question2, question3);
        exam.setQuestions(questions);


        //when
        when(questionRepo.findById(1L)).thenReturn(Optional.of(question1));
        when(questionRepo.findById(2L)).thenReturn(Optional.of(question2));
        when(questionRepo.findById(3L)).thenReturn(Optional.of(question3));

        //then
        assertThat(questionService.countUserExamPoints(exam), equalTo(3));
    }





}