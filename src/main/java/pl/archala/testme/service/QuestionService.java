package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Question;
import pl.archala.testme.models.Questionable;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    private final AnswerRepository answerRepo;

    private final ExamRepository examRepo;

    public QuestionService(QuestionRepository questionRepo, AnswerRepository answerRepo, ExamRepository examRepo) {
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
        this.examRepo = examRepo;
    }

    public int countQuestionPoints(Questionable userQuestion) {
        var templateQuest = questionRepo.findById(userQuestion.getId()).orElseThrow();
        return userQuestion.countPoints(templateQuest);
    }
}