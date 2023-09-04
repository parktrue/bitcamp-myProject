package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/soldier/detail")
public class SoldierDetailController implements PageController {

  SoldierDao soldierDao;

  public SoldierDetailController(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("soldier",
        soldierDao.findBy(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/soldier/detail.jsp";
  }
}
