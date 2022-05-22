package pl.archala.testme.dto;

import lombok.Data;
import pl.archala.testme.entity.Answer;

import java.util.List;

@Data
public class QuestionDTO {
    private String content;
    private List<Answer> answers;
    private String userAnswer;
}
