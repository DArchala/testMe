package pl.archala.testme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn
    private List<Question> questions = new ArrayList<>();

    private String examName;
    private long examQuestionsNumber;
    private String difficultyLevel;
    private long timeInSeconds = 3600;

    public Exam(List<Question> questions, String examName, long examQuestionsNumber, String difficultyLevel, long timeInSeconds) {
        this.questions = questions;
        this.examName = examName;
        this.examQuestionsNumber = examQuestionsNumber;
        this.difficultyLevel = difficultyLevel;
        this.timeInSeconds = timeInSeconds;
    }

    public Exam setAllAnswersFalse() {
        questions.forEach(question -> question.getAnswers().forEach(answer -> answer.setCorrectness(false)));
        return this;
    }

}
