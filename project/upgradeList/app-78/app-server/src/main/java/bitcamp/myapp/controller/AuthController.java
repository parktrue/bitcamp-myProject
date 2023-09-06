package bitcamp.myapp.controller;

import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

  @Autowired
  SoldierService soldierService;

  @RequestMapping("/auth/form")
  public String form() {
    return "/WEB-INF/jsp/auth/form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(
      String milNum,
      String password,
      String saveMilNum,
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    if (saveMilNum != null) {
      Cookie cookie = new Cookie("milNum", milNum);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("milNum", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Soldier loginUser = soldierService.get(milNum, password);
    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=form");
      throw new Exception("로그인 정보가 일치하지 않습니다.");
    }
    request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }

  @RequestMapping("/auth/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
  }
}
