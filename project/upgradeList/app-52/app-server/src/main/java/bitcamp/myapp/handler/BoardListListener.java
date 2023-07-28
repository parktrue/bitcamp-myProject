package bitcamp.myapp.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class BoardListListener implements ActionListener {

  int category;

  BoardDao boardDao;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public BoardListListener(int category, BoardDao boardDao) {
    this.category = category;
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    prompt.println("---------------------------------------");
    prompt.println("번호, 제목, 작성자, 조회수, 등록일");
    prompt.println("---------------------------------------");

    List<Board> list = boardDao.findAll(this.category);

    for (Board board : list) {
      prompt.printf("%d, %s, %s, %d, %s\n", board.getNo(), board.getTitle(),
          board.getWriter().getName(), board.getViewCount(),
          dateFormatter.format(board.getCreatedDate()));
    }
  }

}


