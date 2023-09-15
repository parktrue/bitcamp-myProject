create table myapp_board(
  board_no int not null,
  title varchar(255) not null,
  content text null,
  writer varchar(20) not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now()
);

alter table myapp_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
create table myapp_member(
  member_no int not null,
  name varchar(20) not null,
  email varchar(50) not null,
  password varchar(100) not null,
  gender char(1) not null
);

alter table myapp_member
  add constraint primary key (member_no),
  modify column member_no int not null auto_increment;
  
  
-- 게시판에 카테고리 컬럼 추가
alter table myapp_board
  add column category int not null;
  
  
  
  
  