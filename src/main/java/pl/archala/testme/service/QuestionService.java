package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.AnswerRepository;
import pl.archala.testme.repositories.ExamRepository;
import pl.archala.testme.repositories.QuestionRepository;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public void addSampleQuestions(List<Answer> answers) {
        if (questionRepo.count() == 0) {
            Question q1 = new Question("Co to jest encja?",
                    Arrays.asList(
                            answers.get(0),
                            answers.get(1),
                            answers.get(2),
                            answers.get(3)));
            Question q2 = new Question("Co to jest obiekt?",
                    Arrays.asList(
                            answers.get(4),
                            answers.get(5),
                            answers.get(6),
                            answers.get(7)));
            Question q3 = new Question("Co to jest kompilator?",
                    Arrays.asList(
                            answers.get(8),
                            answers.get(9),
                            answers.get(10),
                            answers.get(11)));
            List<Question> questions = new ArrayList<>(Arrays.asList(q1, q2, q3));
            questionRepo.saveAll(questions);
        }
    }

    public int countUserExamPoints(Exam userExam) {
        int counter = 0;
        for (Question question : userExam.getQuestions()) {
            Question questionTemplate = questionRepo.findById(question.getId())
                    .orElseThrow();
            if (question.isFilledCorrectly(questionTemplate)) counter++;
        }
        return counter;
    }
}