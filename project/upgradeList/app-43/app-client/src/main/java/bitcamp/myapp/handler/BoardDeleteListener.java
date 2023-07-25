package bitcamp.myapp.handler;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;

  public BoardDeleteListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (boardDao.delete(prompt.inputInt("번호? ")) == 0) {
      System.out.println("해당 번호의 게시글이 없습니다!");
    }
  }
}


