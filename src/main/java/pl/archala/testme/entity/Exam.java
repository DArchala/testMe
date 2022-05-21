package pl.archala.testme.entity;

import lombok.EqualsAndHashCode;

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

    public Exam() {
    }

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
        return questions.size() >= 1
                && !examName.isEmpty()
                && Objects.nonNull(difficultyLevel)
                && timeInSeconds >= 60
                && timeInSeconds <= 86400;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public ExamDifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(ExamDifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return timeInSeconds == exam.timeInSeconds && Objects.equals(questions, exam.questions) && Objects.equals(examName, exam.examName) && difficultyLevel == exam.difficultyLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions, examName, difficultyLevel, timeInSeconds);
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
