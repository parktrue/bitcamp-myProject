package bitcamp.myapp.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierDeleteListener implements ActionListener {
  private SoldierDao soldierDao;

  public static Map<Integer, Set<String>> deletedMilitaryNumbers = new HashMap<>();


  public SoldierDeleteListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int number = prompt.inputInt("번호? ");
    Soldier soldier = soldierDao.findBy(number);
    if (soldier == null) {
      System.out.println("해당 번호의 병사가 없습니다!");
    } else {
      soldierDao.delete(number);
      String[] parts = soldier.getMilitaryNumber().split("-");
      int year = Integer.parseInt(parts[0]);
      String militaryNumber = parts[1];
      deletedMilitaryNumbers.putIfAbsent(year, new HashSet<>());
      deletedMilitaryNumbers.get(year).add(militaryNumber);
    }
  }
}
