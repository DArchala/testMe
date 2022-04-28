package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
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
}