package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.*;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.repository.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final QuestionService questionService;

    private final ExamRepository examRepo;

    private final AnswerRepository answerRepo;

    private final QuestionRepository questionRepo;

    private final UserRepository userRepo;

    private final ExamAttemptRepository examAttemptRepo;

    public ExamService(QuestionService questionService, ExamRepository examRepo, AnswerRepository answerRepo, QuestionRepository questionRepo, UserRepository userRepo, ExamAttemptRepository examAttemptRepo) {
        this.questionService = questionService;
        this.examRepo = examRepo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
        this.userRepo = userRepo;
        this.examAttemptRepo = examAttemptRepo;
    }

    public List<Exam> getAllExams() {
        return examRepo.findAll();
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

        for (Question q : exam.getQuestions()) {

            List<Answer> answerList = new ArrayList<>();

            for (Answer a : q.getAnswers()) {
                a.setId(null);
                answerList.add(a);
            }

            answerRepo.saveAll(answerList);

            q.setId(null);
            q.setAnswers(answerList);
            if (q.countCorrectAnswers() == 0)
                throw new IllegalArgumentException("Number of correct answer in any question cannot be less than 1.");
            questionList.add(q);
        }

        questionRepo.saveAll(questionList);

        exam.setId(null);
        exam.setQuestions(questionList);

        examRepo.save(exam);
        return true;
    }

    public Exam findExamById(Long id) {
        return examRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found."));
    }

    public boolean putExam(Exam exam) {

        if (exam.isNew()) throw new EntityNotFoundException("Exam not contain id.");

        Exam examToUpdate = findExamById(exam.getId());

        for (Question q : exam.getQuestions()) {
            questionService.putQuestion(q);
        }

        examToUpdate.setExamName(exam.getExamName());
        examToUpdate.setTimeInSeconds(exam.getTimeInSeconds());
        examToUpdate.setDifficultyLevel(exam.getDifficultyLevel());
        examToUpdate.setQuestions(exam.getQuestions());

        examRepo.save(examToUpdate);
        return true;
    }

    public boolean deleteExam(Long id) {
        findExamById(id);
        examRepo.deleteById(id);
        return true;
    }

    public boolean saveExamAttemptToUser(ExamForm examForm, String username) {
        ExamAttempt examAttempt = new ExamAttempt(examForm);
        examAttempt.setExamUserPoints(countUserExamPoints(examForm.getExam()));
        examAttempt.setExamMaxPoints(getMaxPossibleExamPoints(examForm.getExam().getId()));

        User user = userRepo.findByUsername(username).orElse(null);
        if(user == null) return true;

        examAttempt.setUser(user);
        examAttemptRepo.save(examAttempt);

        user.getExamAttempts().add(examAttempt);
        userRepo.save(user);
        return false;
    }
}