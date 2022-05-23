package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Question;
import pl.archala.testme.entity.Questionable;
import pl.archala.testme.repository.QuestionRepository;

import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    private final AnswerService answerService;

    public QuestionService(QuestionRepository questionRepo, AnswerService answerService) {
        this.questionRepo = questionRepo;
        this.answerService = answerService;
    }

    public int countQuestionPoints(Questionable userQuestion) {
        // getting question template from DB
        var templateQuest = questionRepo.findById(userQuestion.getId()).orElseThrow(
                () -> new NoSuchElementException("Question finding by id=" + userQuestion.getId() + " failed"));
        return userQuestion.countPoints(templateQuest);
    }

    @SuppressWarnings("ConstantConditions")
        public boolean putQuestion(Question question) {

        if (question.isNew()) throw new NoSuchElementException("Question not contain id.");

        Question questionToUpdate = questionRepo.findById(question.getId())
                .orElseThrow(() -> new NoSuchElementException("Question not found."));

        for (Answer a : question.getAnswers()) {
            answerService.putAnswer(a);
        }

        questionToUpdate.setContent(question.getContent());
        questionToUpdate.setAnswers(question.getAnswers());

        questionRepo.save(questionToUpdate);

        return true;
    }
}