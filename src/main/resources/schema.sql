DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE GLOBAL_SEQ;

CREATE TABLE users
(
    id               INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    email            VARCHAR                NOT NULL,
    password         VARCHAR                NOT NULL,
    balance          INTEGER                NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE payments
(
    id               INTEGER DEFAULT GLOBAL_SEQ.nextval PRIMARY KEY,
    user_id          INTEGER                NOT NULL,
    amount           INTEGER                NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);