package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Component;

@Component("/soldier/list")
public class SoldierListListener implements ActionListener {
  SoldierDao soldierDao;

  public SoldierListListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("------------------------------------------------");
    prompt.println("번호, 이름, 나이, 계급, 입대일, 전역일, D-Day, 군번");
    prompt.println("------------------------------------------------");

    List<Soldier> list = soldierDao.findAll();
    for (Soldier s : list) {
      String dischargeDate =
          (s.getDischargeDate() != null) ? s.getDischargeDate().toString() : "전역일 없음";

      String[] militaryNumberParts = s.getMilitaryNumber().split("-");
      String shortenedYear = militaryNumberParts[0].substring(2);
      String shortenedMilitaryNumber = shortenedYear + "-" + militaryNumberParts[1];

      prompt.printf("%d, %s, %d, %s, %s, %s, %d, %s\n", s.getNo(), s.getName(), s.getAge(),
          s.getRank(), s.getEnlistmentDate(), dischargeDate, s.getdDay(), shortenedMilitaryNumber);
    }
  }
}
