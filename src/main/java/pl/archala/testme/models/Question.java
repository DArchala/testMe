package pl.archala.testme.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleChoiceQuestion.class, name = "single"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "multiple"),
        @JsonSubTypes.Type(value = ShortAnswerQuestion.class, name = "short")
})
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

    public Answer getAnswerById(long id) {
        return this.getAnswers().stream()
                .filter(answer -> Objects.equals(answer.getId(), id))
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

    public abstract int countPoints(Questionable question);

    public long countCorrectAnswers() {
        return answers.stream().filter(Answer::isCorrectness).count();
    }

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