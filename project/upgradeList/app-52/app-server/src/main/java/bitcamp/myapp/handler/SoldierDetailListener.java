package bitcamp.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierDetailListener implements ActionListener {

  SoldierDao soldierDao;
  SqlSessionFactory sqlSessionFactory;

  public SoldierDetailListener(SoldierDao soldierDao, SqlSessionFactory sqlSessionFactory) {
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

    prompt.printf("이름: %s\n", s.getName());
    prompt.printf("나이: %d\n", s.getAge());
    prompt.printf("계급: %s\n", s.getRank());
    prompt.printf("입대일: %s\n", s.getEnlistmentDate());
    prompt.printf("전역일: %s\n", s.getDischargeDate());
    prompt.printf("D-Day: %d\n", s.getdDay());
    prompt.printf("군번: %s\n", s.getMilitaryNumber());


    try {
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
