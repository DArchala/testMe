package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.repository.AnswerRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    Answer findAnswerById(Long id) {
        return answerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found."));
    }

    public void putAnswer(Answer answer) {
        if (answer.isNew()) answerRepo.save(answer);
        else {
            Answer newAnswerVersion = findAnswerById(answer.getId());
            newAnswerVersion.setContent(answer.getContent());
            newAnswerVersion.setCorrectness(answer.isCorrectness());
            answerRepo.save(newAnswerVersion);
        }
    }
}
