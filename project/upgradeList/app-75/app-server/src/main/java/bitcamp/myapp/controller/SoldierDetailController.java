package bitcamp.myapp.controller;

import bitcamp.myapp.service.SoldierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/soldier/detail")
public class SoldierDetailController {

  @Autowired
  SoldierService soldierService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("soldier", soldierService.get(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/soldier/detail.jsp";
  }
}
