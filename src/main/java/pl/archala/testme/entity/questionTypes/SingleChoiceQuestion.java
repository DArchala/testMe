package pl.archala.testme.entity.questionTypes;

import com.fasterxml.jackson.annotation.JsonTypeName;
import pl.archala.testme.entity.Answer;
import pl.archala.testme.entity.Question;
import pl.archala.testme.entity.Questionable;

import javax.persistence.Entity;
import java.util.List;

/**
 * Class representing a question, which has only one correct answer
 * on a few possible to choose.
 * User can collect 1 or 0 points for this type question.
 */

@Entity
@JsonTypeName("single")
public class SingleChoiceQuestion extends Question {

    public SingleChoiceQuestion(String content, List<Answer> answers) {
        super();
        setContent(content);
        setAnswers(answers);
    }

    public SingleChoiceQuestion() {
    }

    public int countPoints(Questionable question) {
        SingleChoiceQuestion questionTemplate = (SingleChoiceQuestion) question;
        int counter = 1;
        for (Answer answer : answers) {
            Answer answerTemplate = questionTemplate.getAnswerById(answer.getId());
            if (answers.stream().noneMatch(Answer::isCorrectness) || answer.isCorrectness() != answerTemplate.isCorrectness()) {
                counter = 0;
                return counter;
            }
        }
        return counter;
    }

    @Override
    public boolean areFieldsCorrect() {
        boolean onlyOneAnswerIsCorrect = answers.stream().filter(Answer::isCorrectness).count() == 1;
        return onlyOneAnswerIsCorrect && getAnswers().size() >= 2 && !getContent().isEmpty();
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        if (answers.size() < 2)
            throw new IllegalArgumentException("Number of available answers should be greater or equal to 2.");
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "SingleChoiceQuestion{" +
                "id='" + getId() + '\'' +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                '}';
    }
}