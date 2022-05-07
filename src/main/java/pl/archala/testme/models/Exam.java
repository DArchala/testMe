package pl.archala.testme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "exams")
public class Exam extends AbstractEntity<Long> {

    @OneToMany
    @JoinColumn
    private List<Question> questions = new ArrayList<>();

    private String examName;
    private long examQuestionsNumber;
    private String difficultyLevel;
    private long timeInSeconds = 3600;

    public Exam(List<Question> questions, String examName, long examQuestionsNumber, String difficultyLevel, long timeInSeconds) {
        this.questions = questions;
        this.examName = examName;
        this.examQuestionsNumber = examQuestionsNumber;
        this.difficultyLevel = difficultyLevel;
        this.timeInSeconds = timeInSeconds;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public long getExamQuestionsNumber() {
        return examQuestionsNumber;
    }

    public void setExamQuestionsNumber(long examQuestionsNumber) {
        this.examQuestionsNumber = examQuestionsNumber;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public Exam setAllAnswersFalse() {
        questions.forEach(question -> question.getAnswers().forEach(answer -> answer.setCorrectness(false)));
        return this;
    }

}
