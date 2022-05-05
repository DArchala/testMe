package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
