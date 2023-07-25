package bitcamp.myapp.handler;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierDeleteListener implements ActionListener {
  SoldierDao soldierDao;

  public SoldierDeleteListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (soldierDao.delete(prompt.inputInt("번호? ")) == 0) {
      System.out.println("해당 번호의 병사가 없습니다!");
    }
  }
}
