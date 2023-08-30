package bitcamp.myapp.controller;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/fileDelete")
public class BoardFileDeleteController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.setAttribute("viewUrl", "redirect:../auth/login");
      return;
    }
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");


    Board board = null;
    try {
      int category = Integer.parseInt(request.getParameter("category"));
      int fileNo = Integer.parseInt(request.getParameter("no"));

      AttachedFile attachedFile = boardDao.findFileBy(fileNo);
      board = boardDao.findBy(category, attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글 변경 권한이 없습니다!");
      }

      if (boardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("viewUrl", "redirect:detail?category=" + category + "&no=" + board.getNo());
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh",
          "2;url=/board/detail?category=" + request.getParameter("category") + "&no="
              + board.getNo());
      request.setAttribute("exception", e);
    }
  }
}











