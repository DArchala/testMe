package pl.archala.testme.component;

import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

public class ExamDateTime {

    @Past
    private LocalDateTime startDateTime;

    @PastOrPresent
    private LocalDateTime endDateTime;

    private long userExamTime;

    public ExamDateTime() {
    }

    public ExamDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, long userExamTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.userExamTime = userExamTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getUserExamTime() {
        return userExamTime;
    }

    public void setUserExamTime(long userExamTime) {
        this.userExamTime = userExamTime;
    }

    @Override
    public String toString() {
        return "ExamDateTime{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", userExamTime=" + userExamTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamDateTime that = (ExamDateTime) o;
        return userExamTime == that.userExamTime && Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, userExamTime);
    }
}
