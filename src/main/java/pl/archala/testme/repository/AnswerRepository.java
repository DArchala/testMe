package pl.archala.testme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
