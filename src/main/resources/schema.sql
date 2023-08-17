CREATE TABLE ROLE
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE USERS
(
    ID       BIGSERIAL PRIMARY KEY,
    USERNAME VARCHAR(32) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255),
    EMAIL    VARCHAR(64) NOT NULL UNIQUE,
    ROLE_ID  INTEGER REFERENCES role (ID)
);

INSERT INTO ROLE (ID, NAME)
VALUES (1, 'ROLE_USER');

INSERT INTO ROLE (ID, NAME)
VALUES (2, 'ROLE_ADMIN');

CREATE TABLE CHATS
(
    ID         SERIAL PRIMARY KEY,
    TYPE       VARCHAR(255),
    ADMIN      BIGINT,
    NAME       VARCHAR(255),
    CREATED_AT TIMESTAMPTZ,
    UPDATED_AT TIMESTAMPTZ
);

CREATE TABLE CHAT_USERS
(
    ID         SERIAL PRIMARY KEY,
    CREATED_AT TIMESTAMPTZ,
    UPDATED_AT TIMESTAMPTZ,
    CHAT_ID    BIGINT,
    USER_ID    BIGINT,
    FOREIGN KEY (CHAT_ID) REFERENCES CHATS (ID) ON DELETE CASCADE,
    FOREIGN KEY (USER_ID) REFERENCES USERS (ID) ON DELETE CASCADE
);

CREATE TABLE FOLLOWERS
(
    ID        SERIAL PRIMARY KEY,
    FROM_USER BIGINT,
    TO_USER   BIGINT,
    FOREIGN KEY (FROM_USER) REFERENCES USERS (ID) ON DELETE CASCADE,
    FOREIGN KEY (TO_USER) REFERENCES USERS (ID) ON DELETE CASCADE
);

CREATE TABLE NOTES
(
    ID      SERIAL PRIMARY KEY,
    TITLE   VARCHAR(255),
    TEXT    TEXT,
    IMAGES  TEXT[],
    USER_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USERS (ID) ON DELETE CASCADE
);
