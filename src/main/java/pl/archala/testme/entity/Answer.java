package pl.archala.testme.entity;

import javax.persistence.Entity;

@Entity
public class Answer extends AbstractEntity<Long> {

    private String content;
    private boolean correctness = false;

    public Answer() {
    }

    public Answer(String content) {
        this.content = content;
    }

    public Answer(String content, boolean correctness) {
        this.content = content;
        this.correctness = correctness;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrectness() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }

    public boolean areFieldsCorrect() {
        return !content.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + getId() + '\'' +
                ", content='" + content + '\'' +
                ", correctness=" + correctness +
                '}';
    }
}
