package bitcamp.myapp.handler;

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
    s.setNo(Soldier.soldierId++);
    s.setName(prompt.inputString("이름? "));
    s.setAge(prompt.inputInt("나이? "));
    s.setRank(SoldierActionListener.inputRank(null, prompt));
    s.setEnlistmentDate(prompt.inputLocalDate("입대일 (yyyy-MM-dd 형식): "));

    soldierDao.insert(s);
  }
}
