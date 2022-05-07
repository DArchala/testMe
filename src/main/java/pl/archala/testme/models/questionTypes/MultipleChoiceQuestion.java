package pl.archala.testme.models.questionTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;

/**
 * Class representing a question, which may has multiple correct or wrong answers.
 * User can collect multiple points for this question.
 * Every wrong answer subtract one point (min points = 0)
 * Every correct answer add one point
 */
@NoArgsConstructor
@Entity
public class MultipleChoiceQuestion extends Question {

    public int countQuestionPoints(MultipleChoiceQuestion questionTemplate) {
        int points = 0;
        for (Answer answer : answers) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answer.isCorrectness()) { // if answer was marked
                if(answer.isCorrectness() == answerTemplate.isCorrectness()) points++;
                else points--;
            }
        }
        if (points < 0) points = 0;
        return points;
    }

    public MultipleChoiceQuestion(String content, List<Answer> answers) {
        super(content, answers);
        if (answers.size() < 2)
            throw new IllegalArgumentException("Number of available answers should be greater or equal to 2.");
        if (answers.stream().filter(Answer::isCorrectness).count() == answers.size())
            throw new IllegalArgumentException("Number of correct answers should be less than number of answers.");
        if (answers.stream().noneMatch(Answer::isCorrectness))
            throw new IllegalArgumentException("Number of correct answers should be greater or equal to 1.");
    }
}
