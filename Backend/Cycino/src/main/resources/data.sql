CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    role VARCHAR(20),
    username VARCHAR(50) UNIQUE
);

INSERT INTO users (id, first_name, last_name, password, phone_number, role, username)
VALUES ('id', 'First_Name', 'password123', 'email@example.com', 'LastName', '1234567890', 'admin');

INSERT INTO users (id, first_name, last_name, password, phone_number, role, username)
VALUES ('id2', 'First_Name2', 'password1232', 'email@example.com2', 'LastName2', '12345678902', '2admin');

INSERT INTO users (id, first_name, last_name, password, phone_number, role, username)
VALUES ('id3', 'First_Name3', 'password1233', 'email@example.com3', 'LastName3', '12345678903', '3admin');