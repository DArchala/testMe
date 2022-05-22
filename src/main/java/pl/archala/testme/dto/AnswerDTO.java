package pl.archala.testme.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private String content;
    private boolean correctness;
}
