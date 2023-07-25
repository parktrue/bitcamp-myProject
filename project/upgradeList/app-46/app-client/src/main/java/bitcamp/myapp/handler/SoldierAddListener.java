package bitcamp.myapp.handler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierAddListener implements SoldierActionListener {

  SoldierDao soldierDao;

  public SoldierAddListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Soldier s = new Soldier();
    s.setName(prompt.inputString("이름? "));
    s.setAge(prompt.inputInt("나이? "));
    s.setPassword(prompt.inputString("암호? "));
    s.setRank(SoldierActionListener.inputRank(null, prompt));
    s.setEnlistmentDate(this.inputLocalDate(prompt));

    // set discharge date and D-day
    s.setDischargeDate(s.getEnlistmentDate().plusMonths(18)); // 18 months after enlistment date
    s.setdDay(ChronoUnit.DAYS.between(LocalDate.now(), s.getDischargeDate()));

    soldierDao.insert(s);
  }
}
