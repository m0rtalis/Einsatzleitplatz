CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(50)  NOT NULL collate utf8mb4_general_nopad_ci,
    password VARCHAR(100) NOT NULL,
    enabled  TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(50) NOT NULL collate utf8mb4_general_nopad_ci,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX if not exists ix_auth_username
    on authorities (username, authority);