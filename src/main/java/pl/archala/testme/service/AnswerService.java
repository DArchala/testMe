package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Answer;
import pl.archala.testme.repositories.AnswerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

}