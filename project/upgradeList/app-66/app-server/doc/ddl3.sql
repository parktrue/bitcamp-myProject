-- 게시글첨부파일
DROP TABLE IF EXISTS `myproject_board_file` RESTRICT;

-- 게시글
DROP TABLE IF EXISTS `myproject_board` RESTRICT;

-- 게시판유형
DROP TABLE IF EXISTS `myproject_board_category` RESTRICT;

-- 군인
DROP TABLE IF EXISTS `myproject_soldier` RESTRICT;


-- 군인
CREATE TABLE `myproject_soldier` (
  `soldier_no`     INTEGER      NOT NULL COMMENT '번호', -- 번호
  `soldier_milnum` VARCHAR(50)  NULL     COMMENT '군번', -- 군번
  `name`           VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
  `age`            INTEGER      NOT NULL COMMENT '이메일', -- 이메일
  `password`       VARCHAR(100) NULL     COMMENT '암호', -- 암호
  `rank`           VARCHAR(10)  NOT NULL COMMENT '계급', -- 계급
  `enlis_date`     DATE         NOT NULL COMMENT '입대일', -- 입대일
  `dis_date`       DATE         NULL     COMMENT '전역일', -- 전역일
  `d_day`          INTEGER      NULL     COMMENT '디데이', -- 디데이
  `photo`          VARCHAR(255) NULL     COMMENT '사진' -- 사진
)
COMMENT '군인';

-- 군인
ALTER TABLE `myproject_soldier`
  ADD CONSTRAINT `PK_myproject_soldier` -- 군인 기본키
  PRIMARY KEY (
  `soldier_no` -- 번호
  );

-- 군인 유니크 인덱스
CREATE UNIQUE INDEX `UIX_myproject_soldier`
  ON `myproject_soldier` ( -- 군인
    `soldier_milnum` ASC -- 군번
  );
  
ALTER TABLE myproject_soldier
  MODIFY COLUMN soldier_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '일련번호';

-- 게시글
CREATE TABLE myproject_board (
  board_no     INTEGER      NOT NULL COMMENT '번호', -- 번호
  title        VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content      MEDIUMTEXT   NOT NULL COMMENT '내용', -- 내용
  view_count   INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
  created_date DATETIME     NOT NULL DEFAULT now() COMMENT '등록일', -- 등록일
  writer       INTEGER      NOT NULL COMMENT '작성자', -- 작성자
  category     INTEGER      NOT NULL COMMENT '게시판' -- 게시판
)
COMMENT '게시글';

-- 게시글
ALTER TABLE myproject_board
  ADD CONSTRAINT PK_myproject_board -- 게시글 기본키
  PRIMARY KEY (
  board_no -- 번호
  );

ALTER TABLE myproject_board
  MODIFY COLUMN board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 게시판유형
CREATE TABLE myproject_board_category (
  board_category_no INTEGER     NOT NULL COMMENT '번호', -- 번호
  name              VARCHAR(50) NOT NULL COMMENT '게시판이름' -- 게시판이름
)
COMMENT '게시판유형';

-- 게시판유형
ALTER TABLE myproject_board_category
  ADD CONSTRAINT PK_myproject_board_category -- 게시판유형 기본키
  PRIMARY KEY (
  board_category_no -- 번호
  );

-- 게시판유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_myproject_board_category
  ON myproject_board_category ( -- 게시판유형
    name ASC -- 게시판이름
  );

ALTER TABLE myproject_board_category
  MODIFY COLUMN board_category_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 게시글첨부파일
CREATE TABLE myproject_board_file (
  board_file_no INTEGER      NOT NULL COMMENT '번호', -- 번호
  filepath      VARCHAR(255) NOT NULL COMMENT '파일경로', -- 파일경로
  board_no      INTEGER      NOT NULL COMMENT '게시글번호' -- 게시글번호
)
COMMENT '게시글첨부파일';

-- 게시글첨부파일
ALTER TABLE myproject_board_file
  ADD CONSTRAINT PK_myproject_board_file -- 게시글첨부파일 기본키
  PRIMARY KEY (
  board_file_no -- 번호
  );

ALTER TABLE myproject_board_file
  MODIFY COLUMN board_file_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '번호';

-- 게시글
ALTER TABLE myproject_board
  ADD CONSTRAINT FK_myproject_soldier_TO_myproject_board -- 회원 -> 게시글
  FOREIGN KEY (
  writer -- 작성자
  )
  REFERENCES myproject_soldier ( -- 회원
  soldier_no -- 번호
  );

-- 게시글
ALTER TABLE myproject_board
  ADD CONSTRAINT FK_myproject_board_category_TO_myproject_board -- 게시판유형 -> 게시글
  FOREIGN KEY (
  category -- 게시판
  )
  REFERENCES myproject_board_category ( -- 게시판유형
  board_category_no -- 번호
  );

-- 게시글첨부파일
ALTER TABLE myproject_board_file
  ADD CONSTRAINT FK_myproject_board_TO_myproject_board_file -- 게시글 -> 게시글첨부파일
  FOREIGN KEY (
  board_no -- 게시글번호
  )
  REFERENCES myproject_board ( -- 게시글
  board_no -- 번호
  );