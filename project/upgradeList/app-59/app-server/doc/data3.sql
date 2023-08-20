-- myproject_soldier 테이블 예제 데이터
INSERT INTO myproject_soldier(soldier_no, soldier_milnum, name, age, password, `rank`, enlis_date, dis_date, d_day)
VALUES
    (1, '22-72000001', '홍길동', 22, sha1('1111'), '상병', '2022-11-22', '2024-05-21', 300),
    (2, '22-72000002', '임꺽정', 20, sha1('1111'), '일병', '2022-06-19', '2023-12-18', 145),
    (3, '23-72000001', '막내', 20, sha1('1111'), '이병', '2023-07-25', '2025-01-24', 548),
    (4, '22-72000003', '안중근', 25, sha1('1111'), '병장', '2022-02-15', '2023-08-14', 19),
    (5, '22-72000004', '유관순', 21, sha1('1111'), '상병', '2022-07-05', '2024-01-04', 162),
    (6, '23-72000002', '윤봉길', 25, sha1('1111'), '일병', '2023-04-05', '2024-10-04', 436),
    (7, '07-72000001', '행정관', 38, sha1('1111'), '상사', '2007-04-05', '2009-03-13', 436);


-- myproject_board_category 테이블 예제 데이터
insert into myproject_board_category(board_category_no, name) values(1, '게시판');
insert into myproject_board_category(board_category_no, name) values(2, '독서록');


-- myproject_board 테이블 예제 데이터
insert into myproject_board(board_no, title, content, writer, category)
  values(11, '제목1', '내용', 1, 1);
insert into myproject_board(board_no, title, content, writer, category)
  values(12, '제목2', '내용', 1, 1);
insert into myproject_board(board_no, title, content, writer, category)
  values(13, '제목3', '내용', 3, 1);
insert into myproject_board(board_no, title, content, writer, category)
  values(14, '제목4', '내용', 4, 1);
insert into myproject_board(board_no, title, content, writer, category)
  values(15, '제목5', '내용', 5, 2);
insert into myproject_board(board_no, title, content, writer, category)
  values(16, '제목6', '내용', 5, 2);
insert into myproject_board(board_no, title, content, writer, category)
  values(17, '제목7', '내용', 5, 2);