package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Questionable;
import pl.archala.testme.repositories.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    public int countQuestionPoints(Questionable userQuestion) {
        var templateQuest = questionRepo.findById(userQuestion.getId()).orElseThrow();
        return userQuestion.countPoints(templateQuest);
    }
}