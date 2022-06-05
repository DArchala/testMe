package pl.archala.testme.entity;

import lombok.*;

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
