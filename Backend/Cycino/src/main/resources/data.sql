CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    role VARCHAR(20) UNIQUE
);

CREATE TABLE IF NOT EXISTS login_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(255) UNIQUE,
    user_id BIGINT,  -- Add a foreign key to reference users
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--INSERT INTO login_Info (id, username, password)
--VALUES (124, 'Ben', 'Eschman');
--
--INSERT INTO login_Info (id, username, password)
--VALUES (1384, 'Nate', 'Getman');
--
--INSERT INTO login_Info (id, username, password)
--VALUES (329912, 'Mike', 'Martinez');

