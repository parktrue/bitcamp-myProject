package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;

public class SoldierUpdateListener implements SoldierActionListener {

  SoldierDao soldierDao;
  DataSource ds;

  public SoldierUpdateListener(SoldierDao soldierDao, DataSource ds) {
    this.soldierDao = soldierDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int soldierNo = prompt.inputInt("번호? ");

    Soldier s = soldierDao.findBy(soldierNo);
    if (s == null) {
      prompt.println("해당 번호의 병사가 없습니다!");
      return;
    }
    s.setName(prompt.inputString("이름(%s)? ", s.getName()));
    s.setAge(prompt.inputInt("나이(%d)? ", s.getAge()));
    s.setRank(SoldierActionListener.inputRank(s.getRank(), prompt));

    try {
      soldierDao.update(s);
      ds.getConnection().commit();

    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }
  }
}
