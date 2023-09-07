package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Part;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Controller
@RequestMapping("/soldier/")
public class SoldierController {

  @Autowired
  SoldierService soldierService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public String add() {
    return "/WEB-INF/jsp/soldier/form.jsp";
  }

  @PostMapping("add")
  public String add(Soldier s, Part photofile,
      String enlistmentDateStr, Map<String, Object> model)
      throws Exception {
    try {
      String ageStr = String.valueOf(s.getAge());
      if (ageStr != null && !ageStr.isEmpty()) {
        s.setAge(Integer.parseInt(ageStr));
      }

      if (enlistmentDateStr != null && !enlistmentDateStr.isEmpty()) {
        try {
          LocalDate enlistmentDate = LocalDate.parse(enlistmentDateStr);
          s.setEnlistmentDate(enlistmentDate);

          // 전역일 계산 로직
          LocalDate dischargeDate = enlistmentDate.plusMonths(18).minusDays(1);
          long dDay = ChronoUnit.DAYS.between(LocalDate.now(), dischargeDate);
          s.setDischargeDate(dischargeDate);
          s.setdDay(dDay);

          // 군번 계산 로직
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
          e.printStackTrace();
        }
      }

      if (photofile.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photofile);
        s.setPhoto(uploadFileUrl);
      }
      soldierService.add(s);
      return "redirect:list";

    } catch (Exception e) {
      model.put("message", "병력 등록 오류!");
      model.put("refresh", "2;url=list");
      throw e;
    }
  }


  @GetMapping("delete")
  public String delete(int no, Map<String, Object> model) throws Exception {
    try {
      if (soldierService.delete(no) == 0) {
        throw new Exception("해당 번호의 인원이 없습니다.");
      } else {
        return "redirect:list";
      }
    } catch (Exception e) {
      model.put("refresh", "2;url=list");
      throw e;
    }
  }

  @GetMapping("detail")
  public String detail(int no, Map<String, Object> model) throws Exception {
    model.put("soldier", soldierService.get(no));
    return "/WEB-INF/jsp/soldier/detail.jsp";
  }

  @GetMapping("list")
  public String list(Map<String, Object> model) throws Exception {
    soldierService.updateDday();
    model.put("list", soldierService.list());
    return "/WEB-INF/jsp/soldier/list.jsp";
  }

  @PostMapping("update")
  public String update(Soldier soldier, Part photofile, Map<String, Object> model)
      throws Exception {

    try {
      if (photofile.getSize() > 0) {
        String uploadFileUrl =
            ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photofile);
        soldier.setPhoto(uploadFileUrl);
      }

      if (soldierService.update(soldier) == 0) {
        throw new Exception("해당 인원이 없습니다.");
      } else {
        return "redirect:list";
      }
    } catch (Exception e) {
      model.put("refresh", "2;url=list");
      throw e;
    }
  }
}
