package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierUpdateListener extends AbstractSoldierListener {

  public SoldierUpdateListener(List<Soldier> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int soldierNo = prompt.inputInt("번호? ");

    Soldier s = this.findBy(soldierNo);
    if (s == null) {
      System.out.println("해당 번호의 병사가 없습니다!");
      return;
    }

    String newName = prompt.inputString(String.format("이름(%s)? ", s.getName()));
    if (newName.length() > 0) {
      s.setName(newName);
    }

    int newAge = prompt.inputInt(String.format("나이(%d)? ", s.getAge()));
    if (newAge != 0) {
      s.setAge(newAge);
    }

    String newRank = inputRank(s.getRank(), prompt);
    if (newRank.length() > 0) {
      s.setRank(newRank);
    }
  }
}
