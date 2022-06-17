package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.entity.ExamAttempt;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ExamAttemptService {

    private final UserRepository userRepo;

    public ExamAttemptService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<ExamAttempt> findExamAttemptsByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        return user.getExamAttempts();
    }
}
