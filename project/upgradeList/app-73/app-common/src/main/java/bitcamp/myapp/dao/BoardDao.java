package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import java.util.List;

public interface BoardDao {
  int insert(Board board);

  List<Board> findAll(int category);

  Board findBy(int no);

  int update(Board board);

  int updateCount(int no);

  int delete(int no);

  int insertFiles(Board board);

  AttachedFile findFileBy(int no);

  int deleteFile(int fileNo);

  int deleteFiles(int boardNo);
}
