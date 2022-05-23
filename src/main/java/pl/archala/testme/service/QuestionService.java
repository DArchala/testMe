package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Question;
import pl.archala.testme.entity.Questionable;
import pl.archala.testme.repository.QuestionRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepo, AnswerService answerService) {
        this.questionRepo = questionRepo;
        this.answerService = answerService;
    }

    public int countQuestionPoints(Questionable userQuestion) {
        var templateQuest = findQuestionById(userQuestion.getId());
        return userQuestion.countPoints(templateQuest);
    }

    Question findQuestionById(Long id) {
        return questionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found."));
    }

    public void putQuestion(Question question) {
        for (Answer a : question.getAnswers()) answerService.putAnswer(a);
        questionRepo.save(question);
    }
}