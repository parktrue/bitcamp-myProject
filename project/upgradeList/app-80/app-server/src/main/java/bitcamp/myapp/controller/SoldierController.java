package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.SoldierService;
import bitcamp.myapp.vo.Soldier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/soldier/")
public class SoldierController {

  @Autowired
  SoldierService soldierService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("add")
  public String add(Soldier s, @RequestParam("photofile") MultipartFile photofile, String enlistmentDateStr, Model model)
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
      model.addAttribute("message", "병력 등록 오류!");
      model.addAttribute("refresh", "2;url=list");
      throw e;
    }
  }


  @GetMapping("delete")
  public String delete(int no, Model model) throws Exception {
    try {
      if (soldierService.delete(no) == 0) {
        throw new Exception("해당 번호의 인원이 없습니다.");
      } else {
        return "redirect:list";
      }
    } catch (Exception e) {
      model.addAttribute("refresh", "2;url=list");
      throw e;
    }
  }

  @GetMapping("{no}")
  public String detail(@PathVariable int no, Model model) throws Exception {
    model.addAttribute("soldier", soldierService.get(no));
    return "soldier/detail";
  }

  @GetMapping("list")
  public void list(Model model) throws Exception {
    soldierService.updateDday();
    model.addAttribute("list", soldierService.list());
  }

  @PostMapping("update")
  public String update(Soldier soldier, MultipartFile photofile, Model model) throws Exception {

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
      model.addAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
