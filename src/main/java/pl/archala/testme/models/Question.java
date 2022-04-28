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

    private String content;
    @OneToMany
    private List<Answer> answers;

    public Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

}
