package pl.archala.testme.models;

public interface Questionable {
    int countPoints(Questionable question);
    boolean areFieldsCorrect();
    Long getId();
}
