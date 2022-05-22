package pl.archala.testme.entity.questionTypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Question;
import pl.archala.testme.entity.Questionable;

import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a question, which may has multiple correct or wrong answers.
 * User can collect multiple points for this question.
 * Every wrong answer subtract one point (min points = 0)
 * Every correct answer add one point
 */
@Entity
@JsonTypeName("multiple")
public class MultipleChoiceQuestion extends Question {

    private static final long serialVersionUID = 3L;

    public MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(String content, List<Answer> answers) {
        super();
        setContent(content);
        setAnswers(answers);
    }

    public int countPoints(Questionable question) {
        MultipleChoiceQuestion questionTemplate = (MultipleChoiceQuestion) question;
        int points = 0;
        for (Answer answer : answers) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answer.isCorrectness()) {
                if (answer.isCorrectness() == answerTemplate.isCorrectness()) points++;
                else points--;
            }
        }
        if (points < 0) points = 0;
        return points;
    }

    @Override
    public boolean areFieldsCorrect() {
        boolean atLeastOneAnswerIsCorrect = answers.stream().anyMatch(Answer::isCorrectness);
        return atLeastOneAnswerIsCorrect && getAnswers().size() >= 2 && !getContent().isEmpty();
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        if (answers.size() < 2)
            throw new IllegalArgumentException("Number of available answers should be greater or equal to 2.");
        if (answers.stream().noneMatch(Answer::isCorrectness))
            throw new IllegalArgumentException("Number of correct answers should be greater or equal to 1.");
        this.answers = answers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, answers, serialVersionUID);
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "id='" + getId() + '\'' +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }
}
