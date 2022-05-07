package pl.archala.testme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam extends AbstractEntity<Long> {

    @Size(min=1)
    @OneToMany
    private List<Question> questions = new ArrayList<>();

    private String examName;

    @Size(min=1)
    private long examQuestionsNumber;

    @NotEmpty
    private ExamDifficultyLevel difficultyLevel;

    @Min(60)
    @Max(86_400)
    private long timeInSeconds = 3600;

    public Exam(List<Question> questions, String examName, long examQuestionsNumber, ExamDifficultyLevel difficultyLevel, long timeInSeconds) {
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
