insert into users (id, username, password, role)
values (1, 'user', 'user', 'USER'),
       (2, 'admin', 'admin', 'ADMIN');

insert into answers (id, content, correctness)
values (1, 'Database', true),
       (2, 'DuaLipa', false),
       (3, 'DiagramBeta', false),
       (4, 'Tak', true),
       (5, 'Nie', false),
       (6, 'class', true),
       (7, 'continue', true),
       (8, 'jump', false),
       (9, 'math', false),
       (10, 'money', false),
       (11, 'compiler', false),
       (12, 'Join', false),
       (13, 'Character', true),
       (14, 'Exception', false),
       (15, 'Integer', true),
       (16, 'Class', false),
       (17, 'System', false),
       (18, 'float', true),
       (19, 'double', true),
       (20, 'byte', true),
       (21, 'short', true),
       (22, 'int', true),
       (23, 'long', true);

insert into questions (id, content, user_answer, dtype)
values (1, 'Co to jest DB?', '', 'SingleChoiceQuestion'),
       (2, 'Czy Java jest językiem obiektowym?', '', 'SingleChoiceQuestion'),
       (3, 'Wybierz słowa kluczowe:', '', 'MultipleChoiceQuestion'),
       (4, 'Zaznacz klasy opakowujące:', '', 'MultipleChoiceQuestion'),
       (5, 'Wymień typy zmiennych przechowujących wartości liczb zmiennoprzecinkowych.', '', 'ShortAnswerQuestion'),
       (6, 'Wymień typy zmiennych przechowujących wartości liczb całkowitych.', '', 'ShortAnswerQuestion');

insert into exams (id, difficulty_level, exam_name, exam_time)
values (1, 'MEDIUM', 'Java knowledge', 3600);

insert into questions_answers (question_id, answers_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (4, 12),
       (4, 13),
       (4, 14),
       (4, 15),
       (4, 16),
       (4, 17),
       (5, 18),
       (5, 19),
       (6, 20),
       (6, 21),
       (6, 22),
       (6, 23);

insert into exams_questions (exam_id, questions_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6);
