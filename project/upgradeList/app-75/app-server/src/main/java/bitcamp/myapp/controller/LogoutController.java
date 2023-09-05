package bitcamp.myapp.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/auth/logout")
public class LogoutController {
  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().invalidate();
    return "redirect:/";
  }
}
