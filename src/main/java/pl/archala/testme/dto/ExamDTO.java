package pl.archala.testme.dto;

import lombok.Data;
import pl.archala.testme.entity.ExamDifficultyLevel;
import pl.archala.testme.entity.Question;

import java.util.List;

@Data
public class ExamDTO {
    private List<Question> questions;
    private String examName;
    private ExamDifficultyLevel difficultyLevel;
    private long timeInSeconds;
}
