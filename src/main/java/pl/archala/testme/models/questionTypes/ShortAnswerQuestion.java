package pl.archala.testme.models.questionTypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;
import pl.archala.testme.models.Questionable;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Question which cointains user answer/s
 * for every correct user answer, user gets one point
 */
@Getter
@AllArgsConstructor
@Entity
@JsonTypeName("short")
public class ShortAnswerQuestion extends Question {

    private String userAnswer = "";

    public ShortAnswerQuestion() {
    }

    public ShortAnswerQuestion(String content, List<Answer> answers, String userAnswer) {
        super(content, answers);
        this.userAnswer = userAnswer;
        if (answers.size() < 1)
            throw new IllegalArgumentException("Number of answers in ShortAnswerQuestion has to be greater than 0.");
        if (answers.stream().anyMatch(answer -> !answer.isCorrectness()))
            throw new IllegalArgumentException("Number of incorrect answers in ShortAnswerQuestion has to be equal to 0.");
    }

    public int countPoints(Questionable question) {
        return countPoints();
    }

    public int countPoints() {
        int points = 0;
        Set<String> userAnswers = new HashSet<>();

        for (String userAnswer : List.of(this.getUserAnswer().split(","))) {
            userAnswer = userAnswer.trim();
            userAnswers.add(userAnswer.toLowerCase());
        }

        for (Answer answer : this.answers) {
            if (userAnswers.contains(answer.getContent().toLowerCase())) points++;
        }

        if (userAnswers.size() > this.answers.size())
            points -= (userAnswers.size() - this.answers.size());

        return points;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString() {
        return "ShortAnswerQuestion{" +
                "id='" + getId() + '\'' +
                "content='" + content + '\'' +
                "userAnswer='" + userAnswer + '\'' +
                ", answers=" + answers +
                '}';
    }
}
