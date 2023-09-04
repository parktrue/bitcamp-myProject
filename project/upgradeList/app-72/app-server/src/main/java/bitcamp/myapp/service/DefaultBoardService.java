package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class DefaultBoardService implements BoardService {

  BoardDao boardDao;
  TransactionTemplate txTemplate;


  public DefaultBoardService(BoardDao boardDao, PlatformTransactionManager txManager) {
    this.boardDao = boardDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }


  @Override
  public int add(Board board) throws Exception {
    return txTemplate.execute(status -> {
      int count = boardDao.insert(board);
      if (board.getAttachedFiles().size() > 0) {
        boardDao.insertFiles(board);
      }
      return count;
    });
  }

  @Override
  public List<Board> list(int category) throws Exception {
    return boardDao.findAll(category);
  }

  @Override
  public Board get(int boardNo) throws Exception {
    return boardDao.findBy(boardNo);
  }

  @Override
  public int update(Board board) throws Exception {
    return txTemplate.execute(status -> {
      int count = boardDao.update(board);
      if (count > 0 && board.getAttachedFiles().size() > 0) {
        boardDao.insertFiles(board);
      }
      return count;
    });
  }

  @Override
  public int delete(int boardNo) throws Exception {
    return txTemplate.execute(status -> {
      boardDao.deleteFiles(boardNo);
      int count = boardDao.delete(boardNo);
      return count;
    });
  }

  @Override
  public int increaseViewCount(int boardNo) throws Exception {
    return txTemplate.execute(status -> {
      int count = boardDao.updateCount(boardNo);
      return count;
    });
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return boardDao.findFileBy(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) throws Exception {
    return boardDao.deleteFile(fileNo);
  }
}
