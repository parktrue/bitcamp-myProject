-- myapp_member 테이블 예제 데이터
insert into myapp_member(member_no, name, email, password, gender) 
  values(1, 'aaa', 'aaa@test.com', '1111', 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(2, 'bbb', 'bbb@test.com', '1111', 'M');
insert into myapp_member(member_no, name, email, password, gender) 
  values(3, 'ccc', 'ccc@test.com', '1111', 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(4, 'ddd', 'ddd@test.com', '1111', 'M');
insert into myapp_member(member_no, name, email, password, gender) 
  values(5, 'eee', 'eee@test.com', '1111', 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(6, 'fff', 'fff@test.com', '1111', 'M');

-- myapp_board 테이블 예제 데이터
insert into myapp_board(board_no, title, content, writer, password, category)
  values(11, '제목1', '내용', '홍길동', '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(12, '제목2', '내용', '임꺽정', '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(13, '제목3', '내용', '유관순', '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(14, '제목4', '내용', '이순신', '1111', 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(15, '제목5', '내용', '윤봉길', '1111', 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(16, '제목6', '내용', '안중근', '1111', 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(17, '제목7', '내용', '김구', '1111', 2);