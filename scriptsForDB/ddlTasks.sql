use tasks;

CREATE TABLE task
(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
    description VARCHAR(250),
	status enum('OPENED', 'CLOSED', 'PAUSED') NOT NULL,
	priority enum('HIGH', 'MEDIUM', 'LOW') NOT NULL,
	author_id BIGINT NOT NULL,
    performer_id BIGINT NOT NULL
);

CREATE TABLE user
(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    name VARCHAR(100) NOT NULL
);


ALTER TABLE task ADD FOREIGN KEY (author_id)
    REFERENCES user (id);

ALTER TABLE task ADD FOREIGN KEY (performer_id)
    REFERENCES user (id);


CREATE TABLE comment
(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	text VARCHAR(50) NOT NULL,
    task_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);


ALTER TABLE comment ADD FOREIGN KEY (task_id)
    REFERENCES task (id);

ALTER TABLE comment ADD FOREIGN KEY (user_id)
    REFERENCES user (id);