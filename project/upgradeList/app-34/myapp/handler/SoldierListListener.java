package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierListListener extends AbstractSoldierListener {

  public SoldierListListener(List<Soldier> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 이름, 나이, 계급");
    System.out.println("---------------------------------------");

    for (Soldier s : list) {
      System.out.printf("%d, %s, %d, %s\n", s.getNo(), s.getName(), s.getAge(), s.getRank());
    }
  }
}
