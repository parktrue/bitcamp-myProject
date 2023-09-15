-- myapp_member 테이블 예제 데이터
insert into myapp_member(member_no, name, email, password, gender) 
  values(1, 'aaa', 'aaa@test.com', sha1('1111'), 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(2, 'bbb', 'bbb@test.com', sha1('1111'), 'M');
insert into myapp_member(member_no, name, email, password, gender) 
  values(3, 'ccc', 'ccc@test.com', sha1('1111'), 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(4, 'ddd', 'ddd@test.com', sha1('1111'), 'M');
insert into myapp_member(member_no, name, email, password, gender) 
  values(5, 'eee', 'eee@test.com', sha1('1111'), 'W');
insert into myapp_member(member_no, name, email, password, gender) 
  values(6, 'fff', 'fff@test.com', sha1('1111'), 'M');

-- myapp_board 테이블 예제 데이터
insert into myapp_board(board_no, title, content, writer, password, category)
  values(11, '제목1', '내용', 1, sha1('1111'), 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(12, '제목2', '내용', 1, sha1('1111'), 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(13, '제목3', '내용', 3, sha1('1111'), 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(14, '제목4', '내용', 4, sha1('1111'), 1);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(15, '제목5', '내용', 5, sha1('1111'), 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(16, '제목6', '내용', 5, sha1('1111'), 2);
insert into myapp_board(board_no, title, content, writer, password, category)
  values(17, '제목7', '내용', 5, sha1('1111'), 2);