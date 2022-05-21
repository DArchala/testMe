package pl.archala.testme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.entity.Exam;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {

}
