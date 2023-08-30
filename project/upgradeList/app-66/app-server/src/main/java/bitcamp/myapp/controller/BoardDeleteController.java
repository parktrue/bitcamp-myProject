package bitcamp.myapp.controller;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/delete")
public class BoardDeleteController extends HttpServlet {

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

    try {
      Board b = new Board();
      b.setNo(Integer.parseInt(request.getParameter("no")));
      b.setWriter(loginUser);
      b.setCategory(Integer.parseInt(request.getParameter("category")));

      boardDao.deleteFiles(b.getNo());
      if (boardDao.delete(b) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("viewUrl", "redirect:list?category=" + request.getParameter("category"));
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
      request.setAttribute("exception", e);
    }
  }
}











