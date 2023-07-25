package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierDeleteListener extends AbstractSoldierListener {

  public SoldierDeleteListener(List<Soldier> list) {
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

    this.list.remove(s);
    System.out.println("병사 정보가 삭제되었습니다.");
  }
}
