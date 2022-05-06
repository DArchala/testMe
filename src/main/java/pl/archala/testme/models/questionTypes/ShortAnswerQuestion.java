package pl.archala.testme.models.questionTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShortAnswerQuestion extends Question {

    private String userAnswer = "";

    @Override
    public int countQuestionPoints(Question questionTemplate) {
        var userAnswer = new ShortAnswerQuestion("content", Arrays.asList(), "userAnswer");
        int points = 0;
        if(answers.size() > 1) {
            List<String> userAnswers = new ArrayList<>(List.of(userAnswer.getUserAnswer().split(",")));
            for (Answer answer : answers) {
                if(userAnswers.contains(answer)) points++;
            }
        } else if (answers.size() == 1) {
            if(userAnswer.getUserAnswer().equals(answers.get(0))) points++;
        }
        return points;
    }

    public ShortAnswerQuestion(String content, List<Answer> answers, String userAnswer) {
        super(content, answers);
        this.userAnswer = userAnswer;
        if(answers.size() < 1)
            throw new IllegalArgumentException("Number of answers in ShortAnswerQuestion has to be greater than 0.");
        if(answers.stream().anyMatch(answer -> !answer.isCorrectness()))
            throw new IllegalArgumentException("Number of incorrect answers in ShortAnswerQuestion has to be equal to 0.");
    }

    @Override
    public String toString() {
        return "ShortAnswerQuestion{" +
                "userAnswer='" + userAnswer + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }
}
