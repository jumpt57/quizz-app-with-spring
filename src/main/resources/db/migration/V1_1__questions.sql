CREATE TABLE questions (
  id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  query VARCHAR (50) NOT NULL,
  mandatory BOOLEAN NOT NULL
);