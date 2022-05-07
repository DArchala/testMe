package pl.archala.testme.models.questionTypes;

import lombok.NoArgsConstructor;
import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;

import javax.persistence.Entity;
import java.util.List;

/**
 * Class representing a question, which has only one correct answer
 * on a few possible to choose.
 * User can collect 1 or 0 points for this type question.
 */
@NoArgsConstructor
@Entity
public class SingleChoiceQuestion extends Question {

    public SingleChoiceQuestion(String content, List<Answer> answers) {
        super(content, answers);
        if (answers.size() < 2)
            throw new IllegalArgumentException("Number of available answers should be greater or equal to 2.");
        if (answers.stream().filter(Answer::isCorrectness).count() != 1)
            throw new IllegalArgumentException("Number of correct answers in SingleChoiceQuestion has to be equal to 1.");
    }

    public int countPoints(SingleChoiceQuestion questionTemplate) {
        int counter = 1;
        for (Answer answer : answers) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answer.isCorrectness() != answerTemplate.isCorrectness()) {
                counter = 0;
                return counter;
            }
        }
        return counter;
    }

}