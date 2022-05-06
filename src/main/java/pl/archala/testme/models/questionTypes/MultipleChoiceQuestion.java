package pl.archala.testme.models.questionTypes;

import pl.archala.testme.models.Answer;
import pl.archala.testme.models.Question;

/**
 * Class representing a question, which may has multiple correct or wrong answers.
 * User can collect multiple points for this question.
 */
public class MultipleChoiceQuestion extends Question {

    public int countQuestionPoints(MultipleChoiceQuestion questionTemplate) {
        int counter = 0;
        for (Answer answer : answers) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answer.isCorrectness() == answerTemplate.isCorrectness()) counter++;
            else counter--;
        }
        if (counter < 0) counter = 0;
        return counter;
    }

}
