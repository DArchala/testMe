package pl.archala.testme.entity;

import lombok.*;
import pl.archala.testme.entity.abstractEntities.AbstractEntity;
import pl.archala.testme.enums.ExamDifficultyLevel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @ManyToOne
    private User user;

    private String examName;
    private long examTime;
    private long examUserTime;
    private long examUserPoints;
    private long examMaxPoints;
    private LocalDateTime startTimeDate;
    private LocalDateTime endTimeDate;
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
