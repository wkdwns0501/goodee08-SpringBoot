CREATE TABLE users (
   username VARCHAR(50) NOT NULL PRIMARY KEY, -- 로그인 ID (PK)
   password VARCHAR(500) NOT NULL, -- 인코딩된 비밀번호
   enabled BOOLEAN NOT NULL -- 계정 활성화 여부
);

-- 사용자 1명 : 여러 권한 (1:N)
CREATE TABLE authorities (
     username VARCHAR(50) NOT NULL,
     authority VARCHAR(50) NOT NULL, -- 예: ROLE_USER, ROLE_ADMIN 저장
    -- PRIMARY KEY (username, authority), -- 만약 복합 PK 사용 시 UNIQUE 인덱스는 따로 필요 없음
     CONSTRAINT fk_authorities_user FOREIGN KEY (username) REFERENCES users(username)
);

-- 동일 사용자에게 동일 권한 중복 방지
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

CREATE TABLE member (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256)
);

CREATE TABLE authority (
   id INTEGER AUTO_INCREMENT PRIMARY KEY,
   authority VARCHAR(256),
   member_id INTEGER,
   FOREIGN KEY (member_id) REFERENCES member(id)
);

-- PersistenceToken 저장을 위한 스키마
-- https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html#remember-me-persistent-token
CREATE TABLE persistent_logins (
   username VARCHAR(64) NOT NULL,
   series VARCHAR(64) PRIMARY KEY,
   token VARCHAR(64) NOT NULL,
   last_used DATETIME NOT NULL -- DB 이식성까지 고려하면 TIMESTAMP
);