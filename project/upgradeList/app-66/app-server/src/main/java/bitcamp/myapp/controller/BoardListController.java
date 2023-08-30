package bitcamp.myapp.controller;

import bitcamp.myapp.dao.BoardDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
      request.setAttribute("list", boardDao.findAll(Integer.parseInt(request.getParameter("category"))));
      request.setAttribute("viewUrl", "/WEB-INF/jsp/board/list.jsp");

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=/");
      request.setAttribute("exception", e);
    }
  }

}
