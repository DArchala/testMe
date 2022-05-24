package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Exam;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.repository.AnswerRepository;
import pl.archala.testme.repository.ExamRepository;
import pl.archala.testme.repository.QuestionRepository;

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

    public ExamService(QuestionService questionService, ExamRepository examRepo, AnswerRepository answerRepo, QuestionRepository questionRepo) {
        this.questionService = questionService;
        this.questionRepo = questionRepo;
        this.examRepo = examRepo;
        this.answerRepo = answerRepo;
    }

    public List<Exam> getAllExams() {
        return (List<Exam>) examRepo.findAll();
    }

    public int countUserExamPoints(Exam userExam) {
        int points = 0;

        for (Question q : userExam.getQuestions())
            points += questionService.countQuestionPoints(q);

        return points;
    }

    public int getMaxPossibleExamPoints(Long examId) {
        Optional<Exam> exam = examRepo.findById(examId);
        if (exam.isEmpty()) return -1;
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
}