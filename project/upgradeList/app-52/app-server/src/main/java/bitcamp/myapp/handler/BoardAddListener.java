package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardAddListener implements ActionListener {

  int category;

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddListener(int category, BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.category = category;
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목? "));
    board.setContent(prompt.inputString("내용? "));
    board.setWriter((Soldier) prompt.getAttribute("loginUser"));
    board.setCategory(this.category);

    try {
      boardDao.insert(board);
      // Thread.sleep(5000);
      //
      // boardDao.insert(board);
      // Thread.sleep(5000);
      //
      // boardDao.insert(board);
      // Thread.sleep(5000);

      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}


