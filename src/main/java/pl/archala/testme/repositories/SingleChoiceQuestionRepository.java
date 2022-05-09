package pl.archala.testme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;

@Repository
public interface SingleChoiceQuestionRepository extends CrudRepository<SingleChoiceQuestion, Long> {

}
