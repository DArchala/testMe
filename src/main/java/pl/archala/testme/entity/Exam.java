package pl.archala.testme.entity;

import pl.archala.testme.entity.abstractEntities.AbstractEntity;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.enums.ExamDifficultyLevel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exams")
public class Exam extends AbstractEntity<Long> {

    private static final long serialVersionUID = 2L;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String examName;

    @OneToMany
    @Size(min = 1, max = 500)
    private List<Question> questions = new ArrayList<>();

    @NotNull
    private ExamDifficultyLevel difficultyLevel = ExamDifficultyLevel.MEDIUM;

    @Column(name = "exam_time")
    @Size(min = 60, max = 360_000)
    private long timeInSeconds = 3600;

    public Exam() {
    }

    public Exam(List<Question> questions, String examName, ExamDifficultyLevel difficultyLevel, long timeInSeconds) {
        setQuestions(questions);
        setExamName(examName);
        setDifficultyLevel(difficultyLevel);
        setTimeInSeconds(timeInSeconds);
    }

    public void overwriteFields(Exam newExam) {
        this.examName = newExam.getExamName();
        this.questions = newExam.getQuestions();
        this.difficultyLevel = newExam.getDifficultyLevel();
        this.timeInSeconds = newExam.getTimeInSeconds();
    }

    public Exam setAllAnswersFalse() {
        questions.forEach(question -> question.getAnswers().forEach(answer -> answer.setCorrectness(false)));
        return this;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        if (questions.size() < 1)
            throw new IllegalArgumentException("Number of questions in exam has to be greater or equal to 1.");
        this.questions = questions;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        if (examName.trim().isEmpty())
            throw new IllegalArgumentException("Exam name cannot be empty.");
        this.examName = examName;
    }

    public ExamDifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(ExamDifficultyLevel difficultyLevel) {
        if (Objects.isNull(difficultyLevel))
            throw new IllegalArgumentException("Exam difficulty level cannot be null.");
        this.difficultyLevel = difficultyLevel;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(long timeInSeconds) {
        if (timeInSeconds < 60 || timeInSeconds > 360_000)
            throw new IllegalArgumentException("Exam time in seconds has to be greater than 60 and less than 360000.");
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
