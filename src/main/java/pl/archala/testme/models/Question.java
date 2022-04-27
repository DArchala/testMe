package pl.archala.testme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String questionContent;
    @ElementCollection
    private List<String> answers;

    public Question(String questionContent, List<String> answers) {
        this.questionContent = questionContent;
        this.answers = answers;
    }

}
