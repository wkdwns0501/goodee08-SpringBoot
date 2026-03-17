CREATE TABLE member (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE, -- 로그인 아이디로도 사용
    password VARCHAR(256)
);

CREATE TABLE authority (
   id INTEGER AUTO_INCREMENT PRIMARY KEY,
   authority VARCHAR(256),
   member_id INTEGER,
   FOREIGN KEY (member_id) REFERENCES member(id) -- 회원 아이디로 권한을 조회할 수 있도록 외래키 설정
);

CREATE TABLE article (
     id INTEGER AUTO_INCREMENT PRIMARY KEY,
     title VARCHAR(256),
     description VARCHAR(4096),
     created DATETIME,
     updated DATETIME,
     member_id INTEGER,
     FOREIGN KEY (member_id) REFERENCES member(id)
);
