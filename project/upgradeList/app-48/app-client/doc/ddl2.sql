create table myapp_board(
  board_no int not null,
  title varchar(255) not null,
  content text null,
  writer int not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now(),
  category int not null
);

alter table myapp_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
CREATE TABLE myapp_soldier (
    soldier_no int not null,
    soldier_milnum varchar (50) not null,
    name varchar (50) not null,
    age int not null,
    password varchar (100) not null,
    `rank` varchar (10) not null,
    enlis_date DATE,
    dis_date DATE,
    d_day BIGINT
);

alter table myapp_soldier
  add constraint primary key (soldier_no),
  modify column soldier_no int not null auto_increment;
  
-- 게시판 작성자에 대해 외부키 설정
alter table myapp_board
  add constraint myapp_board_fk foreign key (writer) references myapp_soldier (soldier_no);
  
  
  
  
  