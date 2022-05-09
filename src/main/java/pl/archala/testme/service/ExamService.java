package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

@Service
public class ExamService {

    private final QuestionService questionService;

    public ExamService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public int countUserExamPoints(Exam userExam) {
        int points = 0;
        assert userExam.getId() != null : "userExam id is null";

        // do a loop on every question filled by user
        for (Question q : userExam.getQuestions()) {
            assert q.getId() != null : "question id is null";

            // przesyłamy tylko pytanie przysłane od użytkownika
            points += questionService.countQuestionPoints(q);
        }
        return points;
    }

}