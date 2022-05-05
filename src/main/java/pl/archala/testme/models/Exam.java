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

    public Exam(String examName, long examQuestionsNumber, String difficultyLevel) {
        this.examName = examName;
        this.examQuestionsNumber = examQuestionsNumber;
        this.difficultyLevel = difficultyLevel;
    }

    public Exam(String examName, long examQuestionsNumber, String difficultyLevel, List<Question> questions) {
        this.examName = examName;
        this.examQuestionsNumber = examQuestionsNumber;
        this.difficultyLevel = difficultyLevel;
        this.questions = questions;
    }

    public Exam setAllAnswersFalse() {
        questions.forEach(question -> question.getAnswers().forEach(answer -> answer.setCorrectness(false)));
        return this;
    }


}
