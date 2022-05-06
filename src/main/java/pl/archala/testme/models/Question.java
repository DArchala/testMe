package pl.archala.testme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Parent class for different types of question
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @OneToMany
    private List<Answer> answers;

    public Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public boolean isFilledCorrectly(Question questionTemplate) {
        for (Answer answer : this.getAnswers()) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answer.isCorrectness() != answerTemplate.isCorrectness()) return false;
        }
        return true;
    }

    private Answer getAnswerById(long id) {
        return this.getAnswers().stream()
                .filter(answer -> answer.getId() == id)
                .findFirst().orElseThrow();
    }
}