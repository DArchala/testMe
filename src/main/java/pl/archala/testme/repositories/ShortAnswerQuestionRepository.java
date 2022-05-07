package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;

@Repository
public interface ShortAnswerQuestionRepository extends CrudRepository<ShortAnswerQuestion, Long> {
}
