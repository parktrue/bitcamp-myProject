package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements PageController {
  SoldierDao soldierDao;

  public LoginController(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }
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

    Soldier loginUser = soldierDao.findByMilnumAndPassword(s);
    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=/app/auth/login");
      throw new Exception("로그인 정보가 일치하지 않습니다.");
    } request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }
}
