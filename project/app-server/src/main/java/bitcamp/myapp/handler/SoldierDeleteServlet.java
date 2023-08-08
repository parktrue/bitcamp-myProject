package bitcamp.myapp.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Component;

@Component("/soldier/delete")
public class SoldierDeleteListener implements ActionListener {

  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;

  public static Map<Integer, Set<String>> deletedMilitaryNumbers = new HashMap<>();

  public SoldierDeleteListener(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    try {
      int number = prompt.inputInt("번호? ");
      Soldier soldier = soldierDao.findBy(number);
      if (soldier == null) {
        prompt.println("해당 번호의 병사가 없습니다!");
      } else {
        String militaryNumber = soldier.getMilitaryNumber();

        soldierDao.delete(number);
        prompt.println("삭제했습니다!");

        sqlSessionFactory.openSession(false).commit();

        String[] parts = militaryNumber.split("-");
        int year = Integer.parseInt(parts[0]);
        String militaryPart = parts[1];
        deletedMilitaryNumbers.putIfAbsent(year, new HashSet<>());
        deletedMilitaryNumbers.get(year).add(militaryPart);
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
