package bitcamp.myapp.handler;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierAddListener implements SoldierActionListner {


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
    s.setRank(SoldierActionListner.inputRank(null, prompt));

    soldierDao.insert(s);
  }
}
