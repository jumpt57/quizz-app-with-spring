CREATE TABLE answers (
  id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  question_id uuid NOT NULL,
  user_id uuid NOT NULL,
  score INT NOT NULL,
  date TIMESTAMP NOT NULL,
  FOREIGN KEY (question_id) REFERENCES questions(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);