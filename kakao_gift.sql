create database kakao_gift;

use kakao_gift;

CREATE TABLE gift
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_date       datetime              NULL,
    last_modified_date datetime              NULL,
    name               VARCHAR(255)          NULL,
    price              BIGINT                NULL,
    sender_id          BIGINT                NULL,
    receiver_id        BIGINT                NULL,
    CONSTRAINT pk_gift PRIMARY KEY (id)
);

CREATE TABLE member
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_date       datetime              NULL,
    last_modified_date datetime              NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE member_gift_list
(
    member_id    BIGINT NOT NULL,
    gift_list_id BIGINT NOT NULL
);

ALTER TABLE member_gift_list
    ADD CONSTRAINT uc_member_gift_list_giftlist UNIQUE (gift_list_id);

ALTER TABLE member_gift_list
    ADD CONSTRAINT fk_memgiflis_on_gift FOREIGN KEY (gift_list_id) REFERENCES gift (id);

ALTER TABLE member_gift_list
    ADD CONSTRAINT fk_memgiflis_on_member FOREIGN KEY (member_id) REFERENCES member (id);

INSERT INTO gift (id, created_date, last_modified_date, name, price, sender_id, receiver_id) VALUES (1, '2023-07-21 12:34:56', '2023-07-21 12:34:56', 'Example Gift', 1000, 1, 3);
