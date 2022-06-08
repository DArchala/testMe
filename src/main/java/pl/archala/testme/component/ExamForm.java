package pl.archala.testme.component;

import lombok.*;
import pl.archala.testme.entity.Exam;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExamForm {

    @NotNull
    private ExamDateTime examDateTime;

    @NotNull
    private Exam exam;

}
