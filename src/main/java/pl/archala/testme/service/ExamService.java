package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.ExamRepository;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepo;

    public ExamService(ExamRepository examRepo) {
        this.examRepo = examRepo;
    }





}