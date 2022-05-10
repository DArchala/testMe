package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;

@Service
public class ExamService {

    private final QuestionService questionService;

    public ExamService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public int countUserExamPoints(Exam userExam) {
        int points = 0;

        for (Question q : userExam.getQuestions())
            points += questionService.countQuestionPoints(q);
        
        return points;
    }

}