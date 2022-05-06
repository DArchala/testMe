package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.repositories.AnswerRepository;

@Service
public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }


}