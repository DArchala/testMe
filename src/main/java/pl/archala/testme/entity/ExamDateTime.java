package pl.archala.testme.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExamDateTime {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private long userExamTime;

}
