package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.component.ExamForm;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.entity.User;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.repository.AnswerRepository;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.QuestionRepository;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {

    private final QuestionService questionService;

    private final ExamRepository examRepo;

    private final AnswerRepository answerRepo;

    private final QuestionRepository questionRepo;

    private final UserRepository userRepo;

    public ExamService(QuestionService questionService, ExamRepository examRepo, AnswerRepository answerRepo, QuestionRepository questionRepo, UserRepository userRepo) {
        this.questionService = questionService;
        this.examRepo = examRepo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
        this.userRepo = userRepo;
    }

    public List<Exam> getAllExams() {
        return examRepo.findAll();
    }

    public int countUserExamPoints(Exam userExam) {
        int points = 0;
        for (Question q : userExam.getQuestions()) {
            points += questionService.countQuestionPoints(q);
        }

        return points;
    }

    public int getMaxPossibleExamPoints(Long examId) {
        Exam exam = examRepo.findById(examId).orElse(null);
        if(exam == null) return -1;

        int points = 0;
        for (Question q : exam.getQuestions()) {
            points += q.countCorrectAnswers();
        }

        return points;
    }

    public int saveNewExam(Exam exam) {
        if(examRepo.findByExamName(exam.getExamName()).isPresent()) return 0;
        List<Question> questionList = new ArrayList<>();

        for (Question q : exam.getQuestions()) {
            List<Answer> answerList = new ArrayList<>(q.getAnswers());
            answerRepo.saveAll(answerList);

            q.setAnswers(answerList);

            if (q.countCorrectAnswers() == 0) return 1;
            questionList.add(q);
        }

        questionRepo.saveAll(questionList);

        exam.setQuestions(questionList);
        examRepo.save(exam);

        return 2;
    }

    public Exam findExamById(Long id) {
        return examRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found."));
    }

    public boolean putExam(Exam exam) {
        if (exam.isNew()) throw new EntityNotFoundException("Exam does not contain id.");
        Exam examToUpdate = findExamById(exam.getId());

        for (Question q : exam.getQuestions()) {
            questionService.putQuestion(q);
        }

        examToUpdate.overwriteFields(exam);
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
        if (user == null) return true;

        user.getExamAttempts().add(examAttempt);
        userRepo.save(user);

        return false;
    }
}