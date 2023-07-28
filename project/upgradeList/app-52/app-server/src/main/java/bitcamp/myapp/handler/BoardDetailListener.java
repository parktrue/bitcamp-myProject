package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardDetailListener implements ActionListener {

  int category;

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardDetailListener(int category, BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.category = category;
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int boardNo = prompt.inputInt("번호? ");

    Board board = boardDao.findBy(this.category, boardNo);
    if (board == null) {
      prompt.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    prompt.printf("제목: %s\n", board.getTitle());
    prompt.printf("내용: %s\n", board.getContent());
    prompt.printf("작성자: %s\n", board.getWriter().getName());
    prompt.printf("조회수: %s\n", board.getViewCount());
    prompt.printf("등록일: %tY-%1$tm-%1$td\n", board.getCreatedDate());

    try {
      board.setViewCount(board.getViewCount() + 1);
      boardDao.updateCount(board);
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}


