package pl.archala.testme.component;

import lombok.*;
import pl.archala.testme.entity.Exam;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExamForm {
    
    private ExamDateTime examDateTime;
    private Exam exam;

}
