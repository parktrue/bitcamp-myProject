package bitcamp.myapp.controller;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/")
public class HomeController implements PageController {
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/WEB-INF/jsp/index.jsp";
  }
}
