package pl.archala.testme.models;

import lombok.*;

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
public abstract class Question extends AbstractEntity<Long> implements Serializable {

    protected String content;

    protected Question() {}

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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + this.getId() +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
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