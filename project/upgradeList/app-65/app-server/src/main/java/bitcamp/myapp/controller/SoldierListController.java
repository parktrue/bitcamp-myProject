package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/soldier/list")
public class SoldierListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SoldierDao soldierDao = (SoldierDao) this.getServletContext().getAttribute("soldierDao");
    soldierDao.updateDday();
    request.setAttribute("list", soldierDao.findAll());

    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/soldier/list.jsp").include(request, response);
  }
}
