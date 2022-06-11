insert into users (id, username, password, email, role, is_enabled)
values (1, 'dexter-user', '$2a$12$s7GnHP2rlMAI0GnMdhhr9.Izu1VFUVYPrrJCaj8hogk9h4JloRH3i',
        'damianarchala@gmail.com', 'USER', true),
       (2, 'dexter-admin', '$2a$12$SnohOQj1m5EImc84QdIFCOEQ3dW/6Qs7wbtcUBNSkEOvrsOLgYupi',
        'damianarchala2@gmail.com', 'ADMIN', true);
-- dexter-user password = user123
-- dexter-admin password = admin123

insert into answers (id, content, correctness)
values (1, 'Database', true),
       (2, 'Dormitory', false),
       (3, 'DiagramBeta', false),
       (4, 'Yes', true),
       (5, 'No', false),
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
       (23, 'long', true),
       (24, 'Deletes every other character in a string', false),
       (25, 'Creates an array that stores each character in a string individually', true),
       (26, 'Checks that a string contains numbers and then removes them.', false),
       (27, 'Removes white space from the beginning and end of the string', true),
       (28, 'Reverses the order of characters in a string', false),
       (29, 'Reverses the case of characters in a string', false);

insert into questions (id, content, user_answer, dtype)
values (1, 'What is DB?', '', 'SingleChoiceQuestion'),
       (2, 'Is Java an object oriented language?', '', 'SingleChoiceQuestion'),
       (3, 'Choose keywords:', '', 'MultipleChoiceQuestion'),
       (4, 'Select the wrapping classes:', '', 'MultipleChoiceQuestion'),
       (5, 'List the types of variables that hold the values of floating-point numbers.', '', 'ShortAnswerQuestion'),
       (6, 'List the types of variables holding integer values.', '', 'ShortAnswerQuestion'),
       (7, 'What does the split method of a String class do?', '', 'SingleChoiceQuestion'),
       (8, 'What does the trim method of a String class do?', '', 'SingleChoiceQuestion');

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
       (6, 23),
       (7, 24),
       (7, 25),
       (7, 26),
       (8, 27),
       (8, 28),
       (8, 29);

insert into exams_questions (exam_id, questions_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8);
