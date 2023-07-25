package bitcamp.myapp.handler;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierUpdateListener implements SoldierActionListner {

  SoldierDao soldierDao;

  public SoldierUpdateListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int soldierNo = prompt.inputInt("번호? ");

    Soldier s = soldierDao.findBy(soldierNo);
    if (s == null) {
      System.out.println("해당 번호의 병사가 없습니다!");
      return;
    }
    s.setName(prompt.inputString("이름(%s)? ", s.getName()));
    s.setRank(SoldierActionListner.inputRank(s.getRank(), prompt));

    soldierDao.update(s);
  }
}
