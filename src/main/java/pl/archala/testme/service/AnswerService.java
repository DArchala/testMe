package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.repository.AnswerRepository;

import java.util.NoSuchElementException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }


    @SuppressWarnings("ConstantConditions")
    public boolean putAnswer(Answer answer) {
        if (answer.isNew()) throw new NoSuchElementException("Answer not contain id.");
        Answer answerToUpdate = answerRepo.findById(answer.getId())
                .orElseThrow(() -> new NoSuchElementException("Answer not found."));

        answerToUpdate.setCorrectness(answer.isCorrectness());
        answerToUpdate.setContent(answer.getContent());

        answerRepo.save(answerToUpdate);
        return true;
    }
}
