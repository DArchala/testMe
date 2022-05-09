package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;

@Repository
public interface MultipleChoiceQuestionRepository extends CrudRepository<MultipleChoiceQuestion, Long> {
}
