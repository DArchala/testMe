package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
