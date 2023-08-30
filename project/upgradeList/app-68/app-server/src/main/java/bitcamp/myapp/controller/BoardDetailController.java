package bitcamp.myapp.controller;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/board/detail")
public class BoardDetailController implements PageController {
  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardDetailController(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      int category = Integer.parseInt(request.getParameter("category"));
      int no = Integer.parseInt(request.getParameter("no"));

      Board board = boardDao.findBy(category, no);
      if (board != null) {
        board.setViewCount(board.getViewCount() + 1);
        boardDao.updateCount(board);
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("board", board);
      }
      return "/WEB-INF/jsp/board/detail.jsp";

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh",
          "5;url=/board/list?category=" + request.getParameter("category"));
      throw e;
    }
  }
}
