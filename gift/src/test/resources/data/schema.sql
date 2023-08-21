use kakao_gift;

CREATE TABLE gift
(
    id                 INT AUTO_INCREMENT NOT NULL,
    created_date       datetime              NULL,
    last_modified_date datetime              NULL,
    name               VARCHAR(255)          NULL,
    price              BIGINT                NULL,
    sender_id          BIGINT                NULL,
    receiver_id        BIGINT                NULL,
    stock              INT                   NULL,
    CONSTRAINT pk_gift PRIMARY KEY (id)
);

CREATE TABLE member
(
    id                 INT AUTO_INCREMENT NOT NULL,
    created_date       datetime              NULL,
    last_modified_date datetime              NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE member_gift (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             member_id BIGINT NOT NULL,
                             gift_id BIGINT NOT NULL,
                             PRIMARY KEY (id)
);
