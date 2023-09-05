package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Controller
public class SoldierController {

  @Autowired
  SoldierService soldierService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @RequestMapping("/soldier/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

          // 전역일 계산 로직 추가
          LocalDate dischargeDate = enlistmentDate.plusMonths(18).minusDays(1);
          long dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate);
          s.setDischargeDate(dischargeDate);
          s.setdDay(dDay);

          // 군번 계산 로직 추가
          String enlistmentYear = String.valueOf(enlistmentDate.getYear()).substring(2);
          String lastMilitaryNumber = soldierService.setMilNum(enlistmentYear);
          String newMilitaryNumber;
          if (lastMilitaryNumber == null) {
            newMilitaryNumber = enlistmentYear + "-72000001";
          } else {
            int lastSequence = Integer.parseInt(lastMilitaryNumber.split("-")[1].substring(4));
            newMilitaryNumber = enlistmentYear + "-7200" + String.format("%04d", lastSequence + 1);
          }
          s.setMilitaryNumber(newMilitaryNumber);

        } catch (DateTimeParseException e) {
          // 유효하지 않은 날짜 형식이라면 에러 처리 로직을 추가하거나 로그를 남길 수 있습니다.
          e.printStackTrace();
        }
      }

      Part photoPart = request.getPart("photo");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
        s.setPhoto(uploadFileUrl);
      }

      soldierService.add(s);
      return "redirect:list";

    } catch (Exception e) {

      request.setAttribute("error", e);
      request.setAttribute("message", "병력 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

  @RequestMapping("/soldier/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      if (soldierService.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
        throw new Exception("해당 번호의 인원이 없습니다.");
      } else {
        return "redirect:list";
      }

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list");

      throw e;
    }
  }

  @RequestMapping("/soldier/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("soldier",
        soldierService.get(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/soldier/detail.jsp";
  }

  @RequestMapping("/soldier/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    soldierService.updateDday();
    request.setAttribute("list", soldierService.list());
    return "/WEB-INF/jsp/soldier/list.jsp";
  }

  @RequestMapping("/soldier/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
