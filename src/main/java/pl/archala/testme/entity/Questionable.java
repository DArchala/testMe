package pl.archala.testme.entity;

public interface Questionable {
    int countPoints(Questionable question);
    boolean areFieldsCorrect();
    Long getId();
}
