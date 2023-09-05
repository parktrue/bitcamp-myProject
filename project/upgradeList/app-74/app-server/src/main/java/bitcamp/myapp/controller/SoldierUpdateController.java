package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@Controller("/soldier/update")
public class SoldierUpdateController implements PageController {
  @Autowired
  SoldierService soldierService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

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

      if (soldierService.update(soldier) == 0) {
        throw new Exception("해당 인원이 없습니다.");
      } else {
        return "redirect:list";
      }
    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list");
      throw e;

    }
  }
}
