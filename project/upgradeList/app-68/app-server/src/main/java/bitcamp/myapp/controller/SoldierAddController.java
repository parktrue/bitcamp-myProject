package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Soldier;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component("/soldier/add")
public class SoldierAddController implements PageController {
  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public SoldierAddController(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory,
      NcpObjectStorageService ncpObjectStorageService) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/soldier/form.jsp";
    }
    try {
      Soldier s = new Soldier();
      s.setName(request.getParameter("name"));
      s.setRank(request.getParameter("rank"));

      String ageStr = request.getParameter("age");
      if (ageStr != null && !ageStr.isEmpty()) {
        s.setAge(Integer.parseInt(ageStr));
      }
      s.setPassword(request.getParameter("password"));

      String enlistmentDateStr = request.getParameter("enlistmentDate");
      if (enlistmentDateStr != null && !enlistmentDateStr.isEmpty()) {
        try {
          LocalDate enlistmentDate = LocalDate.parse(enlistmentDateStr);
          s.setEnlistmentDate(enlistmentDate);
        } catch (DateTimeParseException e) {
          e.printStackTrace();
        }
      }

      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
        s.setPhoto(uploadFileUrl);
      }

      soldierDao.insert(s);
      sqlSessionFactory.openSession(false).commit();
      return "redirect:list";

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", "병력 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
