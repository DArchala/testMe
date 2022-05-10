package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Questionable;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    public int countQuestionPoints(Questionable userQuestion) {
        // getting question template from DB
        var templateQuest = questionRepo.findById(userQuestion.getId()).orElseThrow(
                () -> new NoSuchElementException("Question finding by id=" + userQuestion.getId() + " failed"));
        return userQuestion.countPoints(templateQuest);
    }
}