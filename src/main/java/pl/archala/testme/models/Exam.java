package pl.archala.testme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam extends AbstractEntity<Long> {

    @Size(min = 1)
    @OneToMany
    private List<Question> questions = new ArrayList<>();

    @NotEmpty
    private String examName;

    @NotEmpty
    private ExamDifficultyLevel difficultyLevel = ExamDifficultyLevel.MEDIUM;

    @Min(60)        // 60s
    @Max(86_400)    // 24h * 60min * 60s
    private long timeInSeconds = 3600;

    public Exam(List<Question> questions, String examName, ExamDifficultyLevel difficultyLevel, long timeInSeconds) {
        this.questions = questions;
        this.examName = examName;
        this.difficultyLevel = difficultyLevel;
        this.timeInSeconds = timeInSeconds;
    }

    public Exam setAllAnswersFalse() {
        questions.forEach(question -> question.getAnswers().forEach(answer -> answer.setCorrectness(false)));
        return this;
    }

    public boolean areFieldsCorrect() {
        return questions.size() > 1
                && !examName.isEmpty()
                && Objects.nonNull(difficultyLevel)
                && timeInSeconds >= 60
                && timeInSeconds <= 86400;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id='" + getId() + '\'' +
                ", questions=" + questions +
                ", examName='" + examName + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", timeInSeconds=" + timeInSeconds +
                '}';
    }
}
