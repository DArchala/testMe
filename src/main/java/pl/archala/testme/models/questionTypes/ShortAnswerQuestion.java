package pl.archala.testme.models.questionTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Question which cointains user answer/s
 * for every correct user answer, user gets one point
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShortAnswerQuestion extends Question {

    private String userAnswer = "";

    public int countPoints() {
        int points = 0;
        List<String> userAnswers = new ArrayList<>();
        for (String s : List.of(this.getUserAnswer().split(","))) {
            userAnswers.add(s.toLowerCase());
        }
        for (Answer answer : answers) {
            if(userAnswers.contains(answer.getContent().toLowerCase())) points++;
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
                ", id=" + getId() +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }
}
