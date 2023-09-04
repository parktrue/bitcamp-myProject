package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/soldier/list")
public class SoldierListController implements PageController {
  SoldierDao soldierDao;

  public SoldierListController(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    soldierDao.updateDday();
    request.setAttribute("list", soldierDao.findAll());
    return "/WEB-INF/jsp/soldier/list.jsp";
  }
}
