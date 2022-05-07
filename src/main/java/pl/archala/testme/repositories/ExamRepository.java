package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.Exam;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {

}
