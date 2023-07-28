-- myapp_soldier 테이블 예제 데이터
INSERT INTO myapp_soldier(soldier_no, soldier_milnum, name, age, password, `rank`, enlis_date, dis_date, d_day)
VALUES
    (1, '2022-72000001', '홍길동', 22, sha1('1111'), '상병', '2022-11-22', '2024-05-21', 300),
    (2, '2022-72000002', '임꺽정', 20, sha1('1111'), '일병', '2022-06-19', '2023-12-18', 145),
    (3, '2023-72000001', '막내', 20, sha1('1111'), '이병', '2023-07-25', '2025-01-24', 548),
    (4, '2022-72000003', '안중근', 25, sha1('1111'), '병장', '2022-02-15', '2023-08-14', 19),
    (5, '2022-72000004', '유관순', 21, sha1('1111'), '상병', '2022-07-05', '2024-01-04', 162),
    (6, '2023-72000002', '윤봉길', 25, sha1('1111'), '일병', '2023-04-05', '2024-10-04', 436);





-- myapp_board 테이블 예제 데이터
insert into myapp_board(board_no, title, content, writer, password, category)
  values(11, '제목1', '내용', 1, '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(12, '제목2', '내용', 1, '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(13, '제목3', '내용', 3, '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(14, '제목4', '내용', 4, '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(15, '제목5', '내용', 5, '1111', 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(16, '제목6', '내용', 5, '1111', 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(17, '제목7', '내용', 5, '1111', 2);