package pl.archala.testme.component;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExamDateTime {

    @Past
    private LocalDateTime startDateTime;

    @PastOrPresent
    private LocalDateTime endDateTime;

    private long userExamTime;

}
