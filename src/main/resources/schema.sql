CREATE TABLE USERS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ANSWERS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    content VARCHAR(255) NOT NULL,
    correctness BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE QUESTIONS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    content VARCHAR(255) NOT NULL,
    dtype VARCHAR(255) NOT NULL,
    user_answer VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE EXAMS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    difficulty_level ENUM('EASY', 'VERY_EASY', 'MEDIUM', 'HARD', 'VERY_HARD') NOT NULL,
    exam_name VARCHAR(255) NOT NULL,
    exam_time INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE QUESTIONS_ANSWERS (
    question_id INTEGER REFERENCES QUESTIONS(id),
    answers_id INTEGER REFERENCES ANSWERS(id)
);

CREATE TABLE EXAMS_QUESTIONS (
    exam_id INTEGER REFERENCES EXAMS(id),
    questions_id INTEGER REFERENCES QUESTIONS(id)
);
