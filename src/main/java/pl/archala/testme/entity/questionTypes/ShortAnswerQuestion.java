package pl.archala.testme.entity.questionTypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.abstractEntities.Question;
import pl.archala.testme.interfaces.Questionable;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Question which cointains user answer/s
 * for every correct user answer, user gets one point
 */

@Entity
@JsonTypeName("short")
public class ShortAnswerQuestion extends Question {

    private static final long serialVersionUID = 4L;

    private String userAnswer = "";

    public ShortAnswerQuestion() {
    }

    public ShortAnswerQuestion(String content, List<Answer> answers, String userAnswer) {
        super();
        setContent(content);
        setAnswers(answers);
        this.userAnswer = userAnswer;
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

    public String getUserAnswer() {
        return userAnswer;
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        if (answers.size() < 1)
            throw new IllegalArgumentException("Number of answers in ShortAnswerQuestion has to be greater or equal to 1.");
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "ShortAnswerQuestion{" +
                "id='" + getId() + '\'' +
                ", userAnswer='" + userAnswer + '\'' +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShortAnswerQuestion)) return false;
        if (!super.equals(o)) return false;
        ShortAnswerQuestion that = (ShortAnswerQuestion) o;
        return Objects.equals(userAnswer, that.userAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userAnswer, serialVersionUID);
    }
}
