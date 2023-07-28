package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardDeleteListener implements ActionListener {

  int category;

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardDeleteListener(int category, BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.category = category;
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    Board b = new Board();
    b.setNo(prompt.inputInt("번호? "));
    b.setWriter((Soldier) prompt.getAttribute("loginUser"));
    b.setCategory(category);

    try {
      if (boardDao.delete(b) == 0) {
        prompt.println("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        prompt.println("삭제했습니다.");
      }
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      try {
        sqlSessionFactory.openSession(false).rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }
  }
}


