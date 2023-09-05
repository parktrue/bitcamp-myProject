package bitcamp.myapp.controller;

import bitcamp.myapp.service.SoldierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/soldier/list")
public class SoldierListController {

  @Autowired
  SoldierService soldierService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    soldierService.updateDday();
    request.setAttribute("list", soldierService.list());
    return "/WEB-INF/jsp/soldier/list.jsp";
  }
}
