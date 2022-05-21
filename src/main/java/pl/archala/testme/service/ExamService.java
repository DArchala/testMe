package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.Question;
import pl.archala.testme.repository.AnswerRepository;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final QuestionService questionService;

    private final ExamRepository examRepo;

    private final AnswerRepository answerRepo;

    private final QuestionRepository questionRepo;

    public ExamService(QuestionService questionService, ExamRepository examRepo, AnswerRepository answerRepo, QuestionRepository questionRepo) {
        this.questionService = questionService;
        this.examRepo = examRepo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
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
        for (Question q : exam.orElseThrow().getQuestions()) {
            counter += q.countCorrectAnswers();
        }
        return counter;
    }

    public boolean saveNewExam(Exam exam) {
        List<Question> questionList = new ArrayList<>();

        if (!exam.areFieldsCorrect()) return false;

        for (Question q : exam.getQuestions()) {

            if (!q.areFieldsCorrect()) return false;

            List<Answer> answerList = new ArrayList<>();

            for (Answer a : q.getAnswers()) {
                if (!a.areFieldsCorrect()) return false;
                a.setId(null);
                answerList.add(a);
            }

            answerRepo.saveAll(answerList);

            q.setId(null);
            q.setAnswers(answerList);
            questionList.add(q);
        }

        questionRepo.saveAll(questionList);

        exam.setId(null);
        exam.setQuestions(questionList);

        examRepo.save(exam);
        return true;
    }
}