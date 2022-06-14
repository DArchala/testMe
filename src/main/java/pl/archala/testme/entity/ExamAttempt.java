package pl.archala.testme.entity;

import pl.archala.testme.component.ExamForm;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;
import pl.archala.testme.enums.ExamDifficultyLevel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "exam_attempts")
public class ExamAttempt extends AbstractEntity<Long> {

    public static final long serialVersionUID = 9L;

    @NotEmpty
    @Size(min = 1)
    private String examName;

    private long examTime;
    private long examUserTime;
    private long examUserPoints;
    private long examMaxPoints;
    private LocalDateTime startTimeDate;
    private LocalDateTime endTimeDate;

    @NotNull
    private ExamDifficultyLevel examDifficultyLevel;

    public ExamAttempt() {
    }

    public ExamAttempt(String examName, long examTime, long examUserTime, long examUserPoints, long examMaxPoints, LocalDateTime startTimeDate, LocalDateTime endTimeDate, ExamDifficultyLevel examDifficultyLevel) {
        this.examName = examName;
        this.examTime = examTime;
        this.examUserTime = examUserTime;
        this.examUserPoints = examUserPoints;
        this.examMaxPoints = examMaxPoints;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.examDifficultyLevel = examDifficultyLevel;
    }

    public ExamAttempt(ExamForm examForm) {
        this.examName = examForm.getExam().getExamName();
        this.examTime = examForm.getExam().getTimeInSeconds();
        this.examUserTime = examForm.getExamDateTime().getUserExamTime();
        this.startTimeDate = examForm.getExamDateTime().getStartDateTime();
        this.endTimeDate = examForm.getExamDateTime().getEndDateTime();
        this.examDifficultyLevel = examForm.getExam().getDifficultyLevel();
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public long getExamTime() {
        return examTime;
    }

    public void setExamTime(long examTime) {
        this.examTime = examTime;
    }

    public long getExamUserTime() {
        return examUserTime;
    }

    public void setExamUserTime(long examUserTime) {
        this.examUserTime = examUserTime;
    }

    public long getExamUserPoints() {
        return examUserPoints;
    }

    public void setExamUserPoints(long examUserPoints) {
        this.examUserPoints = examUserPoints;
    }

    public long getExamMaxPoints() {
        return examMaxPoints;
    }

    public void setExamMaxPoints(long examMaxPoints) {
        this.examMaxPoints = examMaxPoints;
    }

    public LocalDateTime getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(LocalDateTime startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public LocalDateTime getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(LocalDateTime endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

    public ExamDifficultyLevel getExamDifficultyLevel() {
        return examDifficultyLevel;
    }

    public void setExamDifficultyLevel(ExamDifficultyLevel examDifficultyLevel) {
        this.examDifficultyLevel = examDifficultyLevel;
    }

    @Override
    public String toString() {
        return "ExamAttempt{" +
                "examName='" + examName + '\'' +
                ", examTime=" + examTime +
                ", examUserTime=" + examUserTime +
                ", examUserPoints=" + examUserPoints +
                ", examMaxPoints=" + examMaxPoints +
                ", startTimeDate=" + startTimeDate +
                ", endTimeDate=" + endTimeDate +
                ", examDifficultyLevel=" + examDifficultyLevel +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamAttempt)) return false;
        if (!super.equals(o)) return false;
        ExamAttempt that = (ExamAttempt) o;
        return examTime == that.examTime && examUserTime == that.examUserTime && examUserPoints == that.examUserPoints && examMaxPoints == that.examMaxPoints && Objects.equals(examName, that.examName) && Objects.equals(startTimeDate, that.startTimeDate) && Objects.equals(endTimeDate, that.endTimeDate) && examDifficultyLevel == that.examDifficultyLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), examName, examTime, examUserTime, examUserPoints, examMaxPoints, startTimeDate, endTimeDate, examDifficultyLevel);
    }
}
