package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierUpdateListener implements SoldierActionListener {

  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;

  public SoldierUpdateListener(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory) {
    this.soldierDao = soldierDao;
    this.sqlSessionFactory = sqlSessionFactory;
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
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
