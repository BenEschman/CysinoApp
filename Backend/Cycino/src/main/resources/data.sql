CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    role VARCHAR(20) UNIQUE
);
CREATE TABLE IF NOT EXISTS login_Info (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(255) UNIQUE
);
INSERT INTO login_Info (id, username, password)
VALUES (124, 'Ben', 'Eschman');

INSERT INTO login_Info (id, username, password)
VALUES (1384, 'Nate', 'Getman');

INSERT INTO login_Info (id, username, password)
VALUES (329912, 'Mike', 'Martinez');

