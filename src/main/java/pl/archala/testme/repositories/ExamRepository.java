package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.archala.testme.models.Exam;

public interface ExamRepository extends CrudRepository<Exam, Long> {
}
