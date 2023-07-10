package bitcamp.myapp.handler;

import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public interface SoldierActionListner extends ActionListener {
  static String inputRank(String rank, BreadcrumbPrompt prompt) {
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
