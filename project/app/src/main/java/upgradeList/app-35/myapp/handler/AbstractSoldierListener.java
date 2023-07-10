package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Soldier;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public abstract class AbstractSoldierListener implements ActionListener {

  protected List<Soldier> list;

  public AbstractSoldierListener(List<Soldier> list) {
    this.list = list;
  }

  protected Soldier findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Soldier s = this.list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }

  protected String inputRank(String rank, BreadcrumbPrompt prompt) {
    String label;
    if (rank == null) {
      label = "계급?\n";
    } else {
      label = String.format("계급(%s)?\n", rank);
    }

    while (true) {
      String menuNo =
          prompt.inputString(label + "  1. 이병\n" + "  2. 일병\n" + "  3. 상병\n" + "  4. 병장\n" + "> ");

      switch (menuNo) {
        case "1":
          return "이병";
        case "2":
          return "일병";
        case "3":
          return "상병";
        case "4":
          return "병장";
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

}
