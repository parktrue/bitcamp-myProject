package bitcamp.myapp.controller;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/soldier/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class SoldierAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("viewUrl", "/WEB-INF/jsp/soldier/form.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SoldierDao soldierDao = (SoldierDao) this.getServletContext().getAttribute("soldierDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

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
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
            "bitcamp-nc7-bucket-01", "soldier/", photoPart);
        s.setPhoto(uploadFileUrl);
      }

      soldierDao.insert(s);
      sqlSessionFactory.openSession(false).commit();
      request.setAttribute("viewUrl", "redirect:list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", "병력 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);
    }
  }
}
