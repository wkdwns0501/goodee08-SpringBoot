INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK', TRUE);

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO member(name, email, password)
VALUES ('윤서준', 'SeojunYoon@goodee.co.kr', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK');
INSERT INTO member(name, email, password)
VALUES ('윤광철', 'KwangcheolYoon@goodee.co.kr', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK');
INSERT INTO member(name, email, password)
VALUES ('장준', 'wkdwns0501@naver.com', '$2a$12$hWlC4LQUyqYc2Cw.G4XehOioYNMF44lkNGH2UQ/BjRrGinWmNCNxK');

INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 1);
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 2);
INSERT INTO authority(authority, member_id) VALUES('ROLE_ADMIN', 2);
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 3);
INSERT INTO authority(authority, member_id) VALUES('ROLE_ADMIN', 3);