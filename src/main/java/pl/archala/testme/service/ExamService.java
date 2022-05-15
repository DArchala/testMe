package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.ExamRepository;

import java.util.Optional;

@Service
public class ExamService {

    private final QuestionService questionService;

    private final ExamRepository examRepo;

    public ExamService(QuestionService questionService, ExamRepository examRepository) {
        this.questionService = questionService;
        this.examRepo = examRepository;
    }

    public int countUserExamPoints(Exam userExam) {
        int points = 0;

        for (Question q : userExam.getQuestions())
            points += questionService.countQuestionPoints(q);
        
        return points;
    }

    public int getMaxPossibleExamPoints(Long examId) {
        Optional<Exam> exam = examRepo.findById(examId);
        int counter = 0;
        for(Question q : exam.orElseThrow().getQuestions()) {
            counter += q.countCorrectAnswers();
        }
        return counter;
    }

}