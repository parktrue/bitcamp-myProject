package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.SoldierDao;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Component;

@Component("/auth/login")
public class LoginListener implements SoldierActionListener {
  SoldierDao soldierDao;

  public LoginListener(SoldierDao soldierDao) {
    this.soldierDao = soldierDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    while (true) {

      Soldier s = new Soldier();
      s.setMilitaryNumber(prompt.inputString("군번? "));
      s.setPassword(prompt.inputString("암호? "));

      Soldier loginUser = soldierDao.findByMilnumAndPassword(s);
      if (loginUser == null) {
        prompt.println("병사 정보가 일치하지 않습니다!");

      } else {
        prompt.setAttribute("loginUser", loginUser);
        break;
      }
      prompt.end();
    }
  }
}
