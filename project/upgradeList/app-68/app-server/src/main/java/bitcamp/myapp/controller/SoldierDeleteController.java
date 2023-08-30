package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/soldier/delete")
public class SoldierDeleteController implements PageController {
  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;

  public SoldierDeleteController(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      if (soldierDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
        throw new Exception("해당 번호의 인원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");

      throw e;
    }
  }
}
