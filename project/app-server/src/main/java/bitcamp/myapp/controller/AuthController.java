package bitcamp.myapp.controller;

import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

  @Autowired
  SoldierService soldierService;

  @RequestMapping("/auth/login")
  public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }
    String milNum = request.getParameter("milNum");
    String password = request.getParameter("password");

    if (request.getParameter("saveMilNum") != null) {
      Cookie cookie = new Cookie("milNum", milNum);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("milNum", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Soldier loginUser = soldierService.get(milNum, password);
    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=/app/auth/login");
      throw new Exception("로그인 정보가 일치하지 않습니다.");
    }
    request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }

  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate();
    return "redirect:/";
  }
}
