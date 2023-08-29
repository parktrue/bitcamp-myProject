package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/auth/form.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Soldier s = new Soldier();
    s.setMilitaryNumber(request.getParameter("milNum"));
    s.setPassword(request.getParameter("password"));

    if (request.getParameter("saveMilNum") != null) {
      Cookie cookie = new Cookie("milNum", s.getMilitaryNumber());
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("milNum", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    SoldierDao SoldierDao = (SoldierDao) this.getServletContext().getAttribute("SoldierDao");
    Soldier loginUser = SoldierDao.findByMilnumAndPassword(s);
    if (loginUser != null) {
      // 로그인 정보를 다른 요청에서도 사용할 있도록 세션 보관소에 담아 둔다.
      request.getSession().setAttribute("loginUser", loginUser);
      response.sendRedirect("/");
      return;
    }

    request.setAttribute("refresh", "1;url=/auth/login");
    throw new ServletException("회원정보가 일치하지 않습니다!");
  }
}
