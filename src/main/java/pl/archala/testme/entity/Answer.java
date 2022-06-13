package pl.archala.testme.entity;

import pl.archala.testme.entity.abstractEntities.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "answers")
public class Answer extends AbstractEntity<Long> {

    private static final long serialVersionUID = 6L;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String content;
    private boolean correctness;

    public Answer() {
    }

    public Answer(String content) {
        setContent(content);
    }

    public Answer(String content, boolean correctness) {
        setContent(content);
        setCorrectness(correctness);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content.trim().isEmpty())
            throw new IllegalArgumentException("Answer content cannot be empty.");
        this.content = content;
    }

    public boolean isCorrectness() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }

    public boolean areFieldsCorrect() {
        return !content.trim().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return correctness == answer.correctness && Objects.equals(content, answer.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, correctness, super.getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + getId() + '\'' +
                ", content='" + content + '\'' +
                ", correctness=" + correctness +
                '}';
    }
}
