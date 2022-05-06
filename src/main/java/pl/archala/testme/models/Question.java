package pl.archala.testme.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Parent class for different types of question
 */
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "questions")
public abstract class Question implements Countable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected String content;

    @OneToMany
    protected List<Answer> answers;

    protected Question(long id, String content, List<Answer> answers) {
        this.id = id;
        this.content = content;
        this.answers = answers;
    }

    protected Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public Answer getAnswerById(long id) {
        return this.getAnswers().stream()
                .filter(answer -> answer.getId() == id)
                .findFirst().orElseThrow();
    }

    public abstract int countQuestionPoints(Question questionTemplate);

    public long getId() {
        return id;
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
                "id=" + id +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }
}