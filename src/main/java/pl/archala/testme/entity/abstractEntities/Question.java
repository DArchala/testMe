package pl.archala.testme.entity.abstractEntities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.interfaces.Questionable;
import pl.archala.testme.entity.questionTypes.MultipleChoiceQuestion;
import pl.archala.testme.entity.questionTypes.ShortAnswerQuestion;
import pl.archala.testme.entity.questionTypes.SingleChoiceQuestion;

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
public abstract class Question extends AbstractEntity<Long> implements Questionable {

    protected String content;

    @OneToMany(orphanRemoval = true)
    protected List<Answer> answers;

    public Question() {
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
        if (content.trim().isEmpty())
            throw new IllegalArgumentException("Question content cannot be empty.");
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

    public abstract int hashCode();

    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", answers=" + answers +
                ", id=" + getId() +
                '}';
    }
}