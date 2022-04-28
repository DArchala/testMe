package pl.archala.testme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private boolean correctness;

    public Answer(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Answer(String content, boolean correctness) {
        this.content = content;
        this.correctness = correctness;
    }

    public Answer(String content) {
        this.content = content;
    }
}
