package pl.archala.testme.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import pl.archala.testme.models.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.models.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.models.questionTypes.SingleChoiceQuestion;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Parent class for different types of question
 */
@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question extends AbstractEntity<Long> implements Serializable, Questionable {

    protected String content;

    protected Question() {
    }

    @OneToMany
    protected List<Answer> answers;

    protected Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public Answer getAnswerById(Long id) {
        return this.getAnswers().stream()
                .filter(answer -> {
                    assert answer.getId() != null;
                    return answer.getId().equals(id);
                })
                .findFirst().orElseThrow();
    }

    public String getContent() {
        return content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }


    public abstract String toString();

    public abstract int countPoints(Questionable question);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return Objects.equals(content, question.content) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content, answers);
    }
}