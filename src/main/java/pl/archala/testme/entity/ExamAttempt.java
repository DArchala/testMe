package pl.archala.testme.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.archala.testme.component.ExamForm;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;
import pl.archala.testme.enums.ExamDifficultyLevel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

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

    public ExamAttempt(ExamForm examForm) {
        this.examName = examForm.getExam().getExamName();
        this.examTime = examForm.getExam().getTimeInSeconds();
        this.examUserTime = examForm.getExamDateTime().getUserExamTime();
        this.startTimeDate = examForm.getExamDateTime().getStartDateTime();
        this.endTimeDate = examForm.getExamDateTime().getEndDateTime();
        this.examDifficultyLevel = examForm.getExam().getDifficultyLevel();
    }

}
