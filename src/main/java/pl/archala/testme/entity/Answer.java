package pl.archala.testme.entity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity<Long> {

    private String content;
    private boolean correctness = false;

    public Answer() {
    }

    public Answer(String content) {
        this.content = content;
    }

    public Answer(String content, boolean correctness) {
        this.content = content;
        this.correctness = correctness;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
        return Objects.hash(content, correctness);
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
