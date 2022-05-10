package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Question;
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

    public int countQuestionPoints(Question userQuestion) {
        assert userQuestion.getId() != null : "question id is null";

        if(userQuestion instanceof SingleChoiceQuestion) {
            var userQuest = (SingleChoiceQuestion) userQuestion;
            var templateQuest = questionRepo.findById(userQuest.getId()).orElseThrow();
            return userQuest.countPoints(templateQuest);

        } else if(userQuestion instanceof MultipleChoiceQuestion) {
            var userQuest = (MultipleChoiceQuestion) userQuestion;
            var templateQuest = questionRepo.findById(userQuest.getId()).orElseThrow();
            return userQuest.countPoints(templateQuest);

        } else if(userQuestion instanceof ShortAnswerQuestion) {
            var userQuest = (ShortAnswerQuestion) userQuestion;
            return userQuest.countPoints(null);
        }
        return 0;
    }
}