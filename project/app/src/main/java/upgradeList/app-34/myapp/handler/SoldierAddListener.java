package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.BreadcrumbPrompt;

public class SoldierAddListener extends AbstractSoldierListener {

  public SoldierAddListener(List<Soldier> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Soldier s = new Soldier();
    s.setName(prompt.inputString("이름? "));
    s.setAge(prompt.inputInt("나이? "));
    s.setRank(inputRank(null, prompt));

    this.list.add(s);
  }
}
