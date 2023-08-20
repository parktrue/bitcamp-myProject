-- 군인
DROP TABLE IF EXISTS soldier RESTRICT;

-- 직책
DROP TABLE IF EXISTS position RESTRICT;

-- 소속부대
DROP TABLE IF EXISTS base RESTRICT;

-- 병과
DROP TABLE IF EXISTS speciality RESTRICT;

-- 주특기
DROP TABLE IF EXISTS milocc RESTRICT;

-- 계급분류
DROP TABLE IF EXISTS rankclass RESTRICT;

-- 복무형태
DROP TABLE IF EXISTS mil_service RESTRICT;

-- 계급
DROP TABLE IF EXISTS rank RESTRICT;

-- 부서
DROP TABLE IF EXISTS group RESTRICT;

-- 게시판
DROP TABLE IF EXISTS board RESTRICT;

-- 휴가
DROP TABLE IF EXISTS vacation RESTRICT;

-- 공지사항
DROP TABLE IF EXISTS notice RESTRICT;

-- 조직도
DROP TABLE IF EXISTS groupchart RESTRICT;

-- 군인
CREATE TABLE soldier (
  milno       INTEGER     NOT NULL COMMENT '군번', -- 군번
  groupno     INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
  position_no INTEGER     NOT NULL COMMENT '직책번호', -- 직책번호
  milocc_no   INTEGER     NOT NULL COMMENT '주특기번호', -- 주특기번호
  service_no  INTEGER     NOT NULL COMMENT '복무형태번호', -- 복무형태번호
  rankno      INTEGER     NOT NULL COMMENT '계급번호', -- 계급번호
  name        VARCHAR(60) NOT NULL COMMENT '이름', -- 이름
  age         INTEGER     NOT NULL COMMENT '나이', -- 나이
  email       VARCHAR(40) NOT NULL COMMENT '이메일', -- 이메일
  tel         VARCHAR(30) NOT NULL COMMENT '연락처', -- 연락처
  emtel       INTEGER     NULL     COMMENT '비상연락처' -- 비상연락처
)
COMMENT '군인';

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT PK_soldier -- 군인 기본키
  PRIMARY KEY (
  milno -- 군번
  );

-- 직책
CREATE TABLE position (
  position_no   INTEGER     NOT NULL COMMENT '직책번호', -- 직책번호
  position_name VARCHAR(60) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE position
  ADD CONSTRAINT PK_position -- 직책 기본키
  PRIMARY KEY (
  position_no -- 직책번호
  );

-- 소속부대
CREATE TABLE base (
  baseno   INTEGER     NOT NULL COMMENT '소속부대번호', -- 소속부대번호
  basename VARCHAR(60) NULL     COMMENT '부대명', -- 부대명
  basetel  VARCHAR(30) NULL     COMMENT '연락처' -- 연락처
)
COMMENT '소속부대';

-- 소속부대
ALTER TABLE base
  ADD CONSTRAINT PK_base -- 소속부대 기본키
  PRIMARY KEY (
  baseno -- 소속부대번호
  );

-- 병과
CREATE TABLE speciality (
  speciality_no   INTEGER     NOT NULL COMMENT '병과번호', -- 병과번호
  speciality_name VARCHAR(60) NOT NULL COMMENT '병과명' -- 병과명
)
COMMENT '병과';

-- 병과
ALTER TABLE speciality
  ADD CONSTRAINT PK_speciality -- 병과 기본키
  PRIMARY KEY (
  speciality_no -- 병과번호
  );

-- 주특기
CREATE TABLE milocc (
  milocc_no     INTEGER     NOT NULL COMMENT '주특기번호', -- 주특기번호
  speciality_no INTEGER     NOT NULL COMMENT '병과번호', -- 병과번호
  milocc_name   VARCHAR(60) NOT NULL COMMENT '주특기명' -- 주특기명
)
COMMENT '주특기';

-- 주특기
ALTER TABLE milocc
  ADD CONSTRAINT PK_milocc -- 주특기 기본키
  PRIMARY KEY (
  milocc_no -- 주특기번호
  );

-- 계급분류
CREATE TABLE rankclass (
  rankclass_no   INTEGER     NOT NULL COMMENT '계급분류번호', -- 계급분류번호
  rankclass_name VARCHAR(60) NOT NULL COMMENT '분류명' -- 분류명
)
COMMENT '계급분류';

-- 계급분류
ALTER TABLE rankclass
  ADD CONSTRAINT PK_rankclass -- 계급분류 기본키
  PRIMARY KEY (
  rankclass_no -- 계급분류번호
  );

-- 복무형태
CREATE TABLE mil_service (
  service_no        INTEGER NOT NULL COMMENT '복무형태번호', -- 복무형태번호
  long_service      INTEGER NOT NULL COMMENT '장기복무', -- 장기복무
  short_service     INTEGER NOT NULL COMMENT '단기복무', -- 단기복무
  mandatory_service INTEGER NOT NULL COMMENT '의무복무' -- 의무복무
)
COMMENT '복무형태';

-- 복무형태
ALTER TABLE mil_service
  ADD CONSTRAINT PK_mil_service -- 복무형태 기본키
  PRIMARY KEY (
  service_no -- 복무형태번호
  );

-- 계급
CREATE TABLE rank (
  rankno       INTEGER     NOT NULL COMMENT '계급번호', -- 계급번호
  rankclass_no INTEGER     NOT NULL COMMENT '계급분류번호', -- 계급분류번호
  rankname     VARCHAR(60) NOT NULL COMMENT '계급명' -- 계급명
)
COMMENT '계급';

-- 계급
ALTER TABLE rank
  ADD CONSTRAINT PK_rank -- 계급 기본키
  PRIMARY KEY (
  rankno -- 계급번호
  );

-- 부서
CREATE TABLE group (
  groupno   INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
  baseno    INTEGER     NULL     COMMENT '부대번호', -- 부대번호
  groupname VARCHAR(60) NULL     COMMENT '부서명', -- 부서명
  grouptel  VARCHAR(30) NULL     COMMENT '연락처' -- 연락처
)
COMMENT '부서';

-- 부서
ALTER TABLE group
  ADD CONSTRAINT PK_group -- 부서 기본키
  PRIMARY KEY (
  groupno -- 부서번호
  );

-- 게시판
CREATE TABLE board (
  board_no  INTEGER      NOT NULL COMMENT '게시판번호', -- 게시판번호
  milno     INTEGER      NOT NULL COMMENT '군번', -- 군번
  title     VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content   MEDIUMTEXT   NULL     COMMENT '내용', -- 내용
  regdate   DATE         NOT NULL COMMENT '등록일', -- 등록일
  viewcount INTEGER      NULL     COMMENT '조회수' -- 조회수
)
COMMENT '게시판';

-- 게시판
ALTER TABLE board
  ADD CONSTRAINT PK_board -- 게시판 기본키
  PRIMARY KEY (
  board_no -- 게시판번호
  );

-- 휴가
CREATE TABLE vacation (
  vacation_no INTEGER NOT NULL COMMENT '휴가번호', -- 휴가번호
  milno       INTEGER NOT NULL COMMENT '군번', -- 군번
  start_date  DATE    NOT NULL COMMENT '시작일', -- 시작일
  end_date    DATE    NOT NULL COMMENT '복귀일', -- 복귀일
  exist       BOOLEAN NOT NULL DEFAULT 0 COMMENT '상태' -- 상태
)
COMMENT '휴가';

-- 휴가
ALTER TABLE vacation
  ADD CONSTRAINT PK_vacation -- 휴가 기본키
  PRIMARY KEY (
  vacation_no -- 휴가번호
  );

-- 공지사항
CREATE TABLE notice (
  notice_no INTEGER      NOT NULL COMMENT '공지사항번호', -- 공지사항번호
  board_no  INTEGER      NULL     COMMENT '게시판번호', -- 게시판번호
  title     VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content   MEDIUMTEXT   NULL     COMMENT '내용', -- 내용
  regdate   DATE         NOT NULL COMMENT '등록일', -- 등록일
  viewcount INTEGER      NULL     COMMENT '조회수' -- 조회수
)
COMMENT '공지사항';

-- 공지사항
ALTER TABLE notice
  ADD CONSTRAINT PK_notice -- 공지사항 기본키
  PRIMARY KEY (
  notice_no -- 공지사항번호
  );

-- 조직도
CREATE TABLE groupchart (
  chart_no INTEGER      NOT NULL COMMENT '조직도번호', -- 조직도번호
  board_no INTEGER      NOT NULL COMMENT '게시판번호', -- 게시판번호
  title    VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content  MEDIUMTEXT   NULL     COMMENT '내용' -- 내용
)
COMMENT '조직도';

-- 조직도
ALTER TABLE groupchart
  ADD CONSTRAINT PK_groupchart -- 조직도 기본키
  PRIMARY KEY (
  chart_no -- 조직도번호
  );

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT FK_mil_service_TO_soldier -- 복무형태 -> 군인
  FOREIGN KEY (
  service_no -- 복무형태번호
  )
  REFERENCES mil_service ( -- 복무형태
  service_no -- 복무형태번호
  );

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT FK_position_TO_soldier -- 직책 -> 군인
  FOREIGN KEY (
  position_no -- 직책번호
  )
  REFERENCES position ( -- 직책
  position_no -- 직책번호
  );

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT FK_milocc_TO_soldier -- 주특기 -> 군인
  FOREIGN KEY (
  milocc_no -- 주특기번호
  )
  REFERENCES milocc ( -- 주특기
  milocc_no -- 주특기번호
  );

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT FK_group_TO_soldier -- 부서 -> 군인
  FOREIGN KEY (
  groupno -- 부서번호
  )
  REFERENCES group ( -- 부서
  groupno -- 부서번호
  );

-- 군인
ALTER TABLE soldier
  ADD CONSTRAINT FK_rank_TO_soldier -- 계급 -> 군인
  FOREIGN KEY (
  rankno -- 계급번호
  )
  REFERENCES rank ( -- 계급
  rankno -- 계급번호
  );

-- 주특기
ALTER TABLE milocc
  ADD CONSTRAINT FK_speciality_TO_milocc -- 병과 -> 주특기
  FOREIGN KEY (
  speciality_no -- 병과번호
  )
  REFERENCES speciality ( -- 병과
  speciality_no -- 병과번호
  );

-- 계급
ALTER TABLE rank
  ADD CONSTRAINT FK_rankclass_TO_rank -- 계급분류 -> 계급
  FOREIGN KEY (
  rankclass_no -- 계급분류번호
  )
  REFERENCES rankclass ( -- 계급분류
  rankclass_no -- 계급분류번호
  );

-- 부서
ALTER TABLE group
  ADD CONSTRAINT FK_base_TO_group -- 소속부대 -> 부서
  FOREIGN KEY (
  baseno -- 부대번호
  )
  REFERENCES base ( -- 소속부대
  baseno -- 소속부대번호
  );

-- 게시판
ALTER TABLE board
  ADD CONSTRAINT FK_soldier_TO_board -- 군인 -> 게시판
  FOREIGN KEY (
  milno -- 군번
  )
  REFERENCES soldier ( -- 군인
  milno -- 군번
  );

-- 휴가
ALTER TABLE vacation
  ADD CONSTRAINT FK_soldier_TO_vacation -- 군인 -> 휴가
  FOREIGN KEY (
  milno -- 군번
  )
  REFERENCES soldier ( -- 군인
  milno -- 군번
  );

-- 공지사항
ALTER TABLE notice
  ADD CONSTRAINT FK_board_TO_notice -- 게시판 -> 공지사항
  FOREIGN KEY (
  board_no -- 게시판번호
  )
  REFERENCES board ( -- 게시판
  board_no -- 게시판번호
  );

-- 조직도
ALTER TABLE groupchart
  ADD CONSTRAINT FK_board_TO_groupchart -- 게시판 -> 조직도
  FOREIGN KEY (
  board_no -- 게시판번호
  )
  REFERENCES board ( -- 게시판
  board_no -- 게시판번호
  );