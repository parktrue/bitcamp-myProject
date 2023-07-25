package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierListListener implements ActionListener {
  SoldierDao soldierDao;

  public SoldierListListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("------------------------------------------------");
    System.out.println("번호, 이름, 나이, 계급, 입대일, 전역일, D-Day, 군번");
    System.out.println("------------------------------------------------");

    List<Soldier> list = soldierDao.list();
    for (Soldier s : list) {
      String dischargeDate =
          (s.getDischargeDate() != null) ? s.getDischargeDate().toString() : "전역일 없음";

      String[] militaryNumberParts = s.getMilitaryNumber().split("-");
      String shortenedYear = militaryNumberParts[0].substring(2);
      String shortenedMilitaryNumber = shortenedYear + "-" + militaryNumberParts[1];

      System.out.printf("%d, %s, %d, %s, %s, %s, %d, %s\n", s.getNo(), s.getName(), s.getAge(),
          s.getRank(), s.getEnlistmentDate(), dischargeDate, s.getdDay(), shortenedMilitaryNumber);
    }
  }
}
