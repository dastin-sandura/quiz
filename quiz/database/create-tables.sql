DROP TABLE IF EXISTS questions;

CREATE TABLE questions (
 id NUMERIC NOT NULL ,
 title TEXT,
 description text
);

INSERT INTO questions (id, title, description)
    VALUES (1, 'Java data types', 'What are default data types available in Jira?');

INSERT INTO questions (id, title, description)
    VALUES (2, 'Types of unit tests', 'Which of these are correct test types?');