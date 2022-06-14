package pl.archala.testme.component;

import pl.archala.testme.entity.Exam;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ExamForm {

    @NotNull
    private ExamDateTime examDateTime;

    @NotNull
    private Exam exam;

    public ExamForm() {
    }

    public ExamForm(ExamDateTime examDateTime, Exam exam) {
        this.examDateTime = examDateTime;
        this.exam = exam;
    }

    public ExamDateTime getExamDateTime() {
        return examDateTime;
    }

    public void setExamDateTime(ExamDateTime examDateTime) {
        this.examDateTime = examDateTime;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return "ExamForm{" +
                "examDateTime=" + examDateTime +
                ", exam=" + exam +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamForm examForm = (ExamForm) o;
        return Objects.equals(examDateTime, examForm.examDateTime) && Objects.equals(exam, examForm.exam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examDateTime, exam);
    }
}
