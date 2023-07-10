package bitcamp.myapp.handler;

import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import java.util.List;

public class SoldierListListener implements ActionListener {
  SoldierDao soldierDao;

  public SoldierListListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 이름, 나이, 계급, 입대일, 전역일, D-Day");
    System.out.println("---------------------------------------");

    List<Soldier> list = soldierDao.list();
    for (Soldier s : list) {
      System.out.printf(
          "%d, %s, %d, %s, %s\n",
          s.getNo(), s.getName(), s.getAge(), s.getRank(), s.getEnlistmentDate());
    }
  }
}
