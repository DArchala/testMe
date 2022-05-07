package pl.archala.testme.models;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam extends AbstractEntity<Long> {

    @OneToMany
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
