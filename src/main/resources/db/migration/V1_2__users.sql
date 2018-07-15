CREATE TABLE users (
  id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  username VARCHAR (25) NOT NULL UNIQUE ,
  password VARCHAR (60) NOT NULL,
  enabled BOOLEAN NOT NULL,
  role VARCHAR(25) NOT NULL,
  avatar_url VARCHAR(255)
);