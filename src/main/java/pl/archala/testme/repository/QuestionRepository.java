package pl.archala.testme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.entity.abstractEntities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
