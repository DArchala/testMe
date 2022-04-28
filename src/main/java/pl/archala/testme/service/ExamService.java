package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Exam;
import pl.archala.testme.models.Question;
import pl.archala.testme.repositories.ExamRepository;

import java.util.List;

@Service
public class ExamService {

    public ExamService(ExamRepository examRepo) {
        this.examRepo = examRepo;
    }

    private final ExamRepository examRepo;

    public void addSampleExams(List<Question> questions) {
        if (examRepo.count() == 0) {
            examRepo.save(new Exam("Znajomość języka Java",
                    3,
                    "Easy",
                    questions));
        }
    }


}