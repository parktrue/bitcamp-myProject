package bitcamp.myapp.dao;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDao {
  void insert(Board board);

  List<Board> findAll(int category);

  Board findBy(@Param("categoryNo") int category, @Param("boardNo") int no);

  int update(Board board);

  int updateCount(Board board);

  int delete(Board board);

  int insertFiles(Board board);

  AttachedFile findFileBy(int no);

  int deleteFile(int fileNo);

  int deleteFiles(int boardNo);
}
