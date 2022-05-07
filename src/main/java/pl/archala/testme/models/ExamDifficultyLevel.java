package pl.archala.testme.models;

public enum ExamDifficultyLevel {
    VERY_EASY("Very easy"), EASY("Easy"), MEDIUM("Medium"),
    HARD("Hard"), VERY_HARD("Very hard");

    ExamDifficultyLevel(String hardness) {
        this.hardness = hardness;
    }

    private String hardness;
}
