CREATE TABLE IF NOT EXISTS MEMBER (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);