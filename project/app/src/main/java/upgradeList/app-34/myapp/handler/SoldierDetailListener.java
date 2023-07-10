package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierDetailListener extends AbstractSoldierListener {

  public SoldierDetailListener(List<Soldier> list) {
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

    System.out.printf("이름: %s\n", s.getName());
    System.out.printf("나이: %d\n", s.getAge());
    System.out.printf("계급: %s\n", s.getRank());
  }
}
