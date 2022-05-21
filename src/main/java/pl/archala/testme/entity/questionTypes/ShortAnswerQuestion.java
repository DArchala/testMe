package pl.archala.testme.entity.questionTypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Question;
import pl.archala.testme.entity.Questionable;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    public boolean areFieldsCorrect() {
        boolean allAnswersAreCorrect = answers.stream().allMatch(Answer::isCorrectness);
        return allAnswersAreCorrect && !getAnswers().isEmpty() && !getContent().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShortAnswerQuestion that = (ShortAnswerQuestion) o;
        return Objects.equals(userAnswer, that.userAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userAnswer);
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
}
