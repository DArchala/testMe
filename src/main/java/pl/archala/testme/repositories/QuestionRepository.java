package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.archala.testme.models.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
