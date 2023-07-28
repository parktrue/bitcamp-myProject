package bitcamp.myapp.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;

public class SoldierDeleteListener implements ActionListener {
  SoldierDao soldierDao;
  DataSource ds;

  public static Map<Integer, Set<String>> deletedMilitaryNumbers = new HashMap<>();

  public SoldierDeleteListener(SoldierDao soldierDao, DataSource ds) {
    this.soldierDao = soldierDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    try {
      int number = prompt.inputInt("번호? ");
      Soldier soldier = soldierDao.findBy(number);
      if (soldier == null) {
        prompt.println("해당 번호의 병사가 없습니다!");
      } else {
        soldierDao.delete(number);
        prompt.println("삭제했습니다!");

        ds.getConnection().commit();

        String[] parts = soldier.getMilitaryNumber().split("-");
        int year = Integer.parseInt(parts[0]);
        String militaryNumber = parts[1];
        deletedMilitaryNumbers.putIfAbsent(year, new HashSet<>());
        deletedMilitaryNumbers.get(year).add(militaryNumber);
      }
    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }
  }
}
