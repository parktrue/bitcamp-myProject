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

@WebServlet("/soldier/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class SoldierUpdateController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    SoldierDao soldierDao = (SoldierDao) this.getServletContext().getAttribute("soldierDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    try {
      Soldier soldier = new Soldier();
      soldier.setNo(Integer.parseInt(request.getParameter("no")));
      soldier.setName(request.getParameter("name"));
      soldier.setRank(request.getParameter("rank"));
      soldier.setAge(Integer.parseInt(request.getParameter("age")));
      soldier.setMilitaryNumber(request.getParameter("militaryNumber"));


      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
        soldier.setPhoto(uploadFileUrl);
      }

      if (soldierDao.update(soldier) == 0) {
        throw new Exception("해당 인원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);

    }
  }
}
