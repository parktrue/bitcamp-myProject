package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@Component("/soldier/update")
public class SoldierUpdateController implements PageController {
  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public SoldierUpdateController(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory,
      NcpObjectStorageService ncpObjectStorageService) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Soldier soldier = new Soldier();
      soldier.setNo(Integer.parseInt(request.getParameter("no")));
      soldier.setName(request.getParameter("name"));
      soldier.setRank(request.getParameter("rank"));
      soldier.setAge(Integer.parseInt(request.getParameter("age")));
      soldier.setMilitaryNumber(request.getParameter("militaryNumber"));

      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
        soldier.setPhoto(uploadFileUrl);
      }

      if (soldierDao.update(soldier) == 0) {
        throw new Exception("해당 인원이 없습니다.");
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
